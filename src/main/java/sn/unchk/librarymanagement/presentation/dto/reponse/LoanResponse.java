package sn.unchk.librarymanagement.presentation.dto.reponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import sn.unchk.librarymanagement.domain.models.loan.Loan;
import sn.unchk.librarymanagement.domain.validation.Pattern;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record LoanResponse(
        BookResponse book,
        MemberResponse reader,
        @JsonFormat(pattern = Pattern.DATE)
        LocalDate loanDate,
        @JsonFormat(pattern = Pattern.DATE)
        LocalDate returnedDate,
        @JsonFormat(pattern = Pattern.DATE)
        LocalDateTime createdAt,
        @JsonFormat(pattern = Pattern.DATE)
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {

    public static LoanResponse of(Loan loan) {
        return new LoanResponse(
                BookResponse.of(loan.getBook()),
                MemberResponse.of(loan.getReader()),
                loan.getLoanDate(),
                loan.getReturnedDate(),
                loan.getCreatedAt(),
                loan.getUpdatedAt(),
                loan.getCreatedBy(),
                loan.getUpdatedBy()
        );
    }
}
