package sn.unchk.librarymanagement.presentation.controller.loan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.unchk.librarymanagement.domain.models.loan.LoanStatus;
import sn.unchk.librarymanagement.presentation.dto.reponse.HttpResponse;
import sn.unchk.librarymanagement.presentation.dto.reponse.LoanResponse;
import sn.unchk.librarymanagement.presentation.dto.request.AddLoanRequest;
import sn.unchk.librarymanagement.presentation.dto.request.ReturnLoanRequest;

import java.util.UUID;

import static sn.unchk.librarymanagement.constant.GlobalConstant.LOAN_BASE_ROUTE;

@RequestMapping(value = LOAN_BASE_ROUTE)
public interface LoanController {
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<HttpResponse> addLoan(@RequestBody AddLoanRequest request);

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<HttpResponse> returnLoan(@PathVariable("id") UUID loanId, @RequestBody ReturnLoanRequest request);

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Page<LoanResponse>> getAllLoans(Pageable pageable);

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'READER')")
    ResponseEntity<LoanResponse> getLoanDetails(@PathVariable("id") UUID loanId);

    @GetMapping("/reader")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'READER')")
    ResponseEntity<Page<LoanResponse>> getLoanByReader(@RequestParam("readerId") UUID readerId, Pageable pageable);

    @GetMapping("/status")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Page<LoanResponse>> getLoanByStatus(@RequestParam("status")LoanStatus status, Pageable pageable);

    @GetMapping("/book/current")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Page<LoanResponse>> getCurrentLoansByBook(@RequestParam("bookId") UUID bookId, Pageable pageable);

    @PostMapping("/delay")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<?> remindDelay();

    @PostMapping("/due-date")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<?> remindDueDate();
}
