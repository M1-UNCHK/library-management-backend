package sn.unchk.librarymanagement.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sn.unchk.librarymanagement.domain.exceptions.MalformedFieldException;
import sn.unchk.librarymanagement.domain.models.book.Author;
import sn.unchk.librarymanagement.domain.models.book.Book;
import sn.unchk.librarymanagement.domain.models.book.Category;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


public class BookTests {
    private Author author;
    private Category category;

    @BeforeEach
    public void setUp() {
        author = Author.builder()
                .id(UUID.randomUUID())
                .name("Author1")
                .biography("Author1")
                .dateOfBirth(LocalDate.now().minusYears(30))
                .createdAt(LocalDateTime.now())
                .createdBy("test")
                .build();

        category =  Category.builder()
                .id(UUID.randomUUID())
                .code("CTR-1")
                .name("Category1")
                .createdAt(LocalDateTime.now())
                .createdBy("test")
                .build();
    }
    @Test
    public void shouldAddNewBookWhenGivenValidInformation() {
        Book newBook = Book.add("Book1", LocalDate.now().minusYears(5), 10, author, category);

        assertEquals("Book1", newBook.getName());
        assertEquals(10, newBook.getStock());
        assertEquals(author.getId(), newBook.getAuthor().getId());
        assertEquals(category.getId(), newBook.getCategory().getId());
    }

    @Test
    public void shouldThrowExceptionWhenPublicationDateIsAfterToday() {
        assertThrows(MalformedFieldException.class, () -> Book.add("Book1", LocalDate.now().plusDays(1), 10, author, category));
    }

    @Test
    public void shouldUpdateBookWhenGivenValidInformation() {
        Book book = Book.builder()
                .name("Book 2")
                .publicationDate(LocalDate.now().minusYears(4))
                .stock(20)
                .author(author)
                .category(category)
                .build();

        book.update("Book3", LocalDate.now().minusYears(5), 10, author, category);

        assertEquals("Book3", book.getName());
        assertEquals(LocalDate.now().minusYears(5), book.getPublicationDate());
        assertEquals(10, book.getStock());
        assertEquals(author.getId(), book.getAuthor().getId());
        assertEquals(category.getId(), book.getCategory().getId());
    }

    @Test
    public void shouldIncreaseBookStockWhenGivenNewStock() {
        Book book = Book.builder()
                .stock(20)
                .build();

        book.increaseStock(1);

        assertEquals(21, book.getStock());
    }

    @Test
    public void shouldDecreaseBookStockWhenGivenNewStock() {
        Book book = Book.builder()
                .stock(20)
                .build();

        book.decreaseStock(1);

        assertEquals(19, book.getStock());
    }
}
