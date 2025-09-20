package sn.unchk.librarymanagement.service.loan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.unchk.librarymanagement.domain.models.loan.LoanStatus;
import sn.unchk.librarymanagement.presentation.dto.reponse.LoanResponse;
import sn.unchk.librarymanagement.presentation.dto.request.AddLoanRequest;

import java.time.LocalDate;
import java.util.UUID;

public interface LoanService {
    Boolean addLoan(AddLoanRequest request);
    Boolean returnLoan(UUID loanId, LocalDate date);
    Page<LoanResponse> retrieveAll(Pageable pageable);
    LoanResponse retrieveLoanDetails(UUID loanId);
    Page<LoanResponse> retrieveAllByReader(UUID readerId, Pageable pageable);
    Page<LoanResponse> retrieveAllByStatus(LoanStatus status, Pageable pageable);
    Page<LoanResponse> retrieveAllCurrentLoansForBook(UUID bookId, Pageable pageable);

    void remindLoanDueDate();

    void remindLoanDelay();
}
