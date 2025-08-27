package sn.unchk.librarymanagement.service.book;

import sn.unchk.librarymanagement.domain.models.book.Book;
import sn.unchk.librarymanagement.presentation.dto.reponse.BookResponse;
import sn.unchk.librarymanagement.presentation.dto.request.BookRequest;

import java.util.List;
import java.util.UUID;

public interface BookService {
    Book addBook(BookRequest request);
    Book updateBook(UUID id, BookRequest request);
    List<BookResponse> retrieveAll();
    BookResponse retrieveBookInfo(UUID id);
    List<BookResponse> retrieveBooksOutOfStock();
    List<BookResponse> retrieveAvailableBooks();
    List<BookResponse> retrieveBooksByCategory(UUID categoryId);
    List<BookResponse> retrieveBooksByAuthor(UUID authorId);
}
