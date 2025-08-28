package sn.unchk.librarymanagement.presentation.dto.reponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import sn.unchk.librarymanagement.domain.exceptions.Pattern;
import sn.unchk.librarymanagement.domain.models.book.Book;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record BookResponse(
        UUID id,
        String name,
        LocalDate publicationDate,
        Integer stock,
        AuthorResponse author,
        CategoryResponse category,
        @JsonFormat(pattern = Pattern.DATE)
        LocalDateTime createdAt,
        @JsonFormat(pattern = Pattern.DATE)
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {
    public static BookResponse of(Book book) {
        return new BookResponse(
                book.getId(),
                book.getName(),
                book.getPublicationDate(),
                book.getStock(),
                AuthorResponse.of(book.getAuthor()),
                CategoryResponse.of(book.getCategory()),
                book.getCreatedAt(),
                book.getUpdatedAt(),
                book.getCreatedBy(),
                book.getUpdatedBy()
        );
    }
}
