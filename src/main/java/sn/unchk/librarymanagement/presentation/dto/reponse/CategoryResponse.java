package sn.unchk.librarymanagement.presentation.dto.reponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import sn.unchk.librarymanagement.domain.exceptions.Pattern;
import sn.unchk.librarymanagement.domain.models.book.Category;

import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String code,
        String name,
        String description,
        @JsonFormat(pattern = Pattern.DATE)
        LocalDateTime createdAt,
        @JsonFormat(pattern = Pattern.DATE)
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {
    public static CategoryResponse of(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getCode(),
                category.getName(),
                category.getDescription(),
                category.getCreatedAt(),
                category.getUpdatedAt(),
                category.getCreatedBy(),
                category.getUpdatedBy()
        );
    }
}
