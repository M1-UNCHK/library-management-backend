package sn.unchk.librarymanagement.service.loan;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.unchk.librarymanagement.domain.exceptions.MalformedFieldException;
import sn.unchk.librarymanagement.domain.exceptions.NotFoundException;
import sn.unchk.librarymanagement.domain.models.book.Book;
import sn.unchk.librarymanagement.domain.models.loan.Loan;
import sn.unchk.librarymanagement.domain.models.loan.LoanStatus;
import sn.unchk.librarymanagement.domain.models.member.MemberRole;
import sn.unchk.librarymanagement.domain.models.member.Reader;
import sn.unchk.librarymanagement.event.LoanEvent;
import sn.unchk.librarymanagement.event.EventType;
import sn.unchk.librarymanagement.presentation.dto.reponse.LoanResponse;
import sn.unchk.librarymanagement.presentation.dto.request.AddLoanRequest;
import sn.unchk.librarymanagement.repository.BookRepository;
import sn.unchk.librarymanagement.repository.LoanRepository;
import sn.unchk.librarymanagement.repository.MemberRepository;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class LoanServiceImpl implements LoanService{
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher eventPublisher;

    public LoanServiceImpl(LoanRepository loanRepository, BookRepository bookRepository, MemberRepository memberRepository, ApplicationEventPublisher eventPublisher) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Boolean addLoan(AddLoanRequest request) {
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new NotFoundException("bookId", String.format("Book with id %s not found", request.bookId())));

        Reader reader = (Reader) memberRepository.findByIdAndRole(request.readerId(), MemberRole.READER)
                .orElseThrow(() -> new NotFoundException("readerId", String.format("Reader with id %s not found", request.readerId())));

        if (reader.isInactive())
            throw new MalformedFieldException("readerId", String.format("Reader with id %s is disabled", reader.getId()));

        if (loanRepository.existsByReaderIdAndBookIdAndStatus(
                request.readerId(),
                request.bookId(),
                LoanStatus.IN_PROGRESS)
        )
            throw new MalformedFieldException("bookId","Reader has already loan this book");

        Loan loan = Loan.addNewLoan(book, reader, request.date());

        loanRepository.save(loan);

        book.decreaseStock(1);
        bookRepository.save(book);

        eventPublisher.publishEvent(new LoanEvent(this, reader.getEmail(), reader.getLastname(), loan, EventType.LOAN_ADDED));

        return true;
    }

    @Override
    public Boolean returnLoan(UUID loanId, LocalDate date) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new NotFoundException("loanId", String.format("Loan with id %s not found", loanId)));

        loan.returnLoan(date);

        loanRepository.save(loan);

        Book book = loan.getBook();
        book.increaseStock(1);
        bookRepository.save(book);

        eventPublisher.publishEvent(new LoanEvent(this, loan.getReader().getEmail(), loan.getReader().getLastname(), loan, EventType.LOAN_RETURNED));

        return true;
    }

    @Override
    public Page<LoanResponse> retrieveAll(Pageable pageable) {
        return loanRepository.findAll(pageable)
                .map(LoanResponse::of);
    }

    @Override
    public LoanResponse retrieveLoanDetails(UUID loanId) {
        return loanRepository.findById(loanId)
                .map(LoanResponse::of)
                .orElseThrow(() -> new NotFoundException("loanId", String.format("Loan with id %s not found", loanId)));
    }

    @Override
    public Page<LoanResponse> retrieveAllByReader(UUID readerId, Pageable pageable) {
        return loanRepository.findAllByReaderId(readerId, pageable)
                .map(LoanResponse::of);
    }

    @Override
    public Page<LoanResponse> retrieveAllByStatus(LoanStatus status, Pageable pageable) {
        return loanRepository.findAllByStatus(status, pageable)
                .map(LoanResponse::of);
    }

    @Override
    public Page<LoanResponse> retrieveAllCurrentLoansForBook(UUID bookId, Pageable pageable) {
        return loanRepository.findAllByBookIdAndStatus(bookId, LoanStatus.IN_PROGRESS, pageable)
                .map(LoanResponse::of);
    }
}
