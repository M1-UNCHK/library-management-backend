package sn.unchk.librarymanagement.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import sn.unchk.librarymanagement.domain.validation.Authentication;

import static sn.unchk.librarymanagement.constant.GlobalConstant.REQUIRED_FIELD_NAME;

public record MemberLoginRequest(
        @NotNull(message = REQUIRED_FIELD_NAME, groups = Authentication.class)
        String username,

        @NotNull(message = REQUIRED_FIELD_NAME, groups = Authentication.class)
        String password
) {
}
