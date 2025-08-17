package sn.unchk.librarymanagement.presentation.dto.request;


import jakarta.validation.constraints.NotNull;
import sn.unchk.librarymanagement.domain.models.member.MemberStatus;
import sn.unchk.librarymanagement.domain.validation.Update;

import static sn.unchk.librarymanagement.constant.GlobalConstant.REQUIRED_FIELD_NAME;

public record MemberStatusUpdateRequest(
        @NotNull(message = REQUIRED_FIELD_NAME, groups = Update.class)
        MemberStatus status
) {
}
