package sn.unchk.librarymanagement.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import sn.unchk.librarymanagement.domain.validation.Update;

import static sn.unchk.librarymanagement.constant.GlobalConstant.REQUIRED_FIELD_NAME;

public record NewPasswordRequest(
        @NotNull(message = REQUIRED_FIELD_NAME, groups = Update.class)
        String password
) {
}
