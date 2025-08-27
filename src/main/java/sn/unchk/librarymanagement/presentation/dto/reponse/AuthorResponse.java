package sn.unchk.librarymanagement.presentation.dto.reponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import sn.unchk.librarymanagement.domain.exceptions.Pattern;
import sn.unchk.librarymanagement.domain.models.book.Author;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record AuthorResponse(
        UUID id,
        String name,
        LocalDate dateOfBirth,
        String biography,
        @JsonFormat(pattern = Pattern.DATE)
        LocalDateTime createdAt,
        @JsonFormat(pattern = Pattern.DATE)
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {


    public static AuthorResponse of(Author author) {
        return new AuthorResponse(
                author.getId(),
                author.getName(),
                author.getDateOfBirth(),
                author.getBiography(),
                author.getCreatedAt(),
                author.getUpdatedAt(),
                author.getCreatedBy(),
                author.getUpdatedBy()
        );
    }
}
