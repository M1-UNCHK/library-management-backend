package sn.unchk.librarymanagement.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import sn.unchk.librarymanagement.domain.models.member.MemberRole;
import sn.unchk.librarymanagement.domain.validation.Create;
import sn.unchk.librarymanagement.domain.validation.Update;

import static sn.unchk.librarymanagement.constant.GlobalConstant.REQUIRED_FIELD_NAME;
import static sn.unchk.librarymanagement.domain.exceptions.Pattern.EMAIL;


@Builder
public record MemberRequest(
        @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class})
        String firstname,

        @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class})
        String lastname,

        @NotNull(message = REQUIRED_FIELD_NAME, groups = Create.class)
        String username,

        @NotNull(message = REQUIRED_FIELD_NAME, groups = Create.class)
        String password,

        @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class})
        @Pattern(regexp = EMAIL)
        String email,

        @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class})
        String phoneNumber,

        @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class})
        String address,
        @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class})
        MemberRole role
) {

}
