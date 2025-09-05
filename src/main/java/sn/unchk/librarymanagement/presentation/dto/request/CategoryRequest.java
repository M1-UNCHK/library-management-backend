package sn.unchk.librarymanagement.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sn.unchk.librarymanagement.domain.validation.Create;

import static sn.unchk.librarymanagement.constant.GlobalConstant.REQUIRED_FIELD_NAME;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CategoryRequest {
    @NotNull(message = REQUIRED_FIELD_NAME, groups = Create.class)
    private String code;

    @NotNull(message = REQUIRED_FIELD_NAME, groups = Create.class)
    private String name;

    @NotNull(message = REQUIRED_FIELD_NAME, groups = Create.class)
    @Size(max = 10000)
    private String description;
}