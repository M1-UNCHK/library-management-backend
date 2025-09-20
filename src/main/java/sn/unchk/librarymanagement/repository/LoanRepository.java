package sn.unchk.librarymanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import sn.unchk.librarymanagement.domain.models.loan.Loan;
import sn.unchk.librarymanagement.domain.models.loan.LoanStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LoanRepository extends BaseRepository<Loan> {
    Page<Loan> findAllByReaderId(UUID readerId, Pageable pageable);

    Page<Loan> findAllByStatus(LoanStatus status, Pageable pageable);

    Page<Loan> findAllByBookIdAndStatus(UUID bookId, LoanStatus status, Pageable pageable);

    boolean existsByReaderIdAndBookIdAndStatus(UUID readerId, UUID bookId, LoanStatus loanStatus);

    List<Loan> findAllByDueDateBetweenAndStatusAndHasNotifyForRemindFalse(LocalDate now, LocalDate duDate, LoanStatus status);

    List<Loan> findAllByDueDateBeforeAndStatusAndHasNotifyForDelayFalse(LocalDate now, LoanStatus loanStatus);
}
