package sn.unchk.librarymanagement.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sn.unchk.librarymanagement.domain.validation.Create;
import sn.unchk.librarymanagement.domain.validation.Update;

import java.time.LocalDate;
import java.util.UUID;

import static sn.unchk.librarymanagement.constant.GlobalConstant.REQUIRED_FIELD_NAME;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class BookRequest {
    @NotNull(message = REQUIRED_FIELD_NAME, groups =  Create.class)
    private String name;

    @NotNull(message = REQUIRED_FIELD_NAME, groups = Create.class)
    private LocalDate publicationDate;

    @NotNull(message = REQUIRED_FIELD_NAME, groups = Create.class)
    private Integer stock;

    @NotNull(message = REQUIRED_FIELD_NAME, groups = Create.class)
    private UUID categoryId;

    @NotNull(message = REQUIRED_FIELD_NAME, groups = Create.class)
    private UUID authorId;
}
