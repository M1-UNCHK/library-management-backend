package sn.unchk.librarymanagement.presentation.controller.loan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sn.unchk.librarymanagement.domain.models.loan.LoanStatus;
import sn.unchk.librarymanagement.domain.validation.Create;
import sn.unchk.librarymanagement.domain.validation.Update;
import sn.unchk.librarymanagement.presentation.dto.reponse.HttpResponse;
import sn.unchk.librarymanagement.presentation.dto.reponse.LoanResponse;
import sn.unchk.librarymanagement.presentation.dto.request.AddLoanRequest;
import sn.unchk.librarymanagement.presentation.dto.request.ReturnLoanRequest;
import sn.unchk.librarymanagement.presentation.validation.RequestValidator;
import sn.unchk.librarymanagement.service.loan.LoanService;

import java.util.UUID;

import static sn.unchk.librarymanagement.constant.GlobalConstant.CREATED_MESSAGE;
import static sn.unchk.librarymanagement.constant.GlobalConstant.UPDATED_MESSAGE;

@RestController
public class LoanControllerImpl implements LoanController {
    private static final String ENTITY = "Loan";
    private final LoanService loanService;
    private final RequestValidator validator;

    public LoanControllerImpl(LoanService loanService, RequestValidator validator) {
        this.loanService = loanService;
        this.validator = validator;
    }

    @Override
    public ResponseEntity<HttpResponse> addLoan(AddLoanRequest request) {
        validator.assertValidity(request, Create.class);

        loanService.addLoan(request);

        return ResponseEntity.ok().body(HttpResponse.success(String.format(CREATED_MESSAGE, ENTITY)));
    }

    @Override
    public ResponseEntity<HttpResponse> returnLoan(UUID loanId, ReturnLoanRequest request) {
        validator.assertValidity(request, Update.class);

        loanService.returnLoan(loanId, request.date());

        return ResponseEntity.ok().body(HttpResponse.success(String.format(UPDATED_MESSAGE, ENTITY)));
    }

    @Override
    public ResponseEntity<Page<LoanResponse>> getAllLoans(Pageable pageable) {
        return ResponseEntity.ok().body(loanService.retrieveAll(pageable));
    }

    @Override
    public ResponseEntity<LoanResponse> getLoanDetails(UUID loanId) {
        return ResponseEntity.ok().body(loanService.retrieveLoanDetails(loanId));
    }

    @Override
    public ResponseEntity<Page<LoanResponse>> getLoanByReader(UUID readerId, Pageable pageable) {
        return ResponseEntity.ok().body(loanService.retrieveAllByReader(readerId, pageable));
    }

    @Override
    public ResponseEntity<Page<LoanResponse>> getLoanByStatus(LoanStatus status, Pageable pageable) {
        return ResponseEntity.ok().body(loanService.retrieveAllByStatus(status, pageable));
    }

    @Override
    public ResponseEntity<Page<LoanResponse>> getCurrentLoansByBook(UUID bookId, Pageable pageable) {
        return ResponseEntity.ok().body(loanService.retrieveAllCurrentLoansForBook(bookId, pageable));
    }
}
