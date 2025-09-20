package sn.unchk.librarymanagement.presentation.dto.reponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import sn.unchk.librarymanagement.domain.models.loan.Loan;
import sn.unchk.librarymanagement.domain.models.loan.LoanStatus;
import sn.unchk.librarymanagement.domain.validation.Pattern;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record LoanResponse(
        UUID id,
        BookResponse book,
        MemberResponse reader,
        LocalDate loanDate,
        LocalDate returnedDate,
        LoanStatus status,
        @JsonFormat(pattern = Pattern.DATE)
        LocalDateTime createdAt,
        @JsonFormat(pattern = Pattern.DATE)
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {

    public static LoanResponse of(Loan loan) {
        return new LoanResponse(
                loan.getId(),
                BookResponse.of(loan.getBook()),
                MemberResponse.of(loan.getReader()),
                loan.getLoanDate(),
                loan.getReturnedDate(),
                loan.getStatus(),
                loan.getCreatedAt(),
                loan.getUpdatedAt(),
                loan.getCreatedBy(),
                loan.getUpdatedBy()
        );
    }
}
