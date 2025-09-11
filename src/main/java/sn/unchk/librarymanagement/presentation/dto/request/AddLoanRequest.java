package sn.unchk.librarymanagement.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import sn.unchk.librarymanagement.domain.validation.Create;

import java.time.LocalDate;
import java.util.UUID;

import static sn.unchk.librarymanagement.constant.GlobalConstant.REQUIRED_FIELD_NAME;

public record AddLoanRequest(
        @NotNull(message = REQUIRED_FIELD_NAME, groups = Create.class)
        UUID bookId, UUID readerId,

        @NotNull(message = REQUIRED_FIELD_NAME, groups = Create.class)
        LocalDate date) {

}
