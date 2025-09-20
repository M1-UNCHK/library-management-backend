package sn.unchk.librarymanagement.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import sn.unchk.librarymanagement.domain.validation.Create;
import sn.unchk.librarymanagement.domain.validation.Update;

import java.time.LocalDate;

import static sn.unchk.librarymanagement.constant.GlobalConstant.REQUIRED_FIELD_NAME;

public record ReturnLoanRequest(

        @NotNull(message = REQUIRED_FIELD_NAME, groups = Update.class)
        LocalDate date
){
}
