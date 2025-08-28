package sn.unchk.librarymanagement.presentation.controller.book;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import sn.unchk.librarymanagement.domain.validation.Create;
import sn.unchk.librarymanagement.domain.validation.Update;
import sn.unchk.librarymanagement.presentation.dto.reponse.BookResponse;
import sn.unchk.librarymanagement.presentation.dto.reponse.HttpResponse;
import sn.unchk.librarymanagement.presentation.dto.request.BookRequest;
import sn.unchk.librarymanagement.presentation.validation.RequestValidator;
import sn.unchk.librarymanagement.service.book.BookService;

import java.util.List;
import java.util.UUID;

import static sn.unchk.librarymanagement.constant.GlobalConstant.CREATED_MESSAGE;
import static sn.unchk.librarymanagement.constant.GlobalConstant.UPDATED_MESSAGE;

@RestController
public class BookControllerImpl implements BookController{
    private static final String ENTITY = "Book";

    private final BookService bookService;
    private final RequestValidator validator;


    public BookControllerImpl(BookService bookService, RequestValidator validator) {
        this.bookService = bookService;
        this.validator = validator;
    }

    @Override
    public ResponseEntity<HttpResponse> addBook(BookRequest request) {
        validator.assertValidity(request, Create.class);

        bookService.addBook(request);

        return ResponseEntity.ok().body(HttpResponse.success(String.format(CREATED_MESSAGE, ENTITY)));
    }

    @Override
    public ResponseEntity<HttpResponse> updateBook(UUID bookId, BookRequest request) {
        validator.assertValidity(request, Update.class);

        bookService.updateBook(bookId, request);

        return ResponseEntity.ok().body(HttpResponse.success(String.format(UPDATED_MESSAGE, ENTITY)));
    }

    @Override
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        return ResponseEntity.ok().body(bookService.retrieveAll());
    }

    @Override
    public ResponseEntity<BookResponse> getBookInfo(UUID bookId) {
        return ResponseEntity.ok().body(bookService.retrieveBookInfo(bookId));

    }

    @Override
    public ResponseEntity<List<BookResponse>> getAvailableBooks() {
        return ResponseEntity.ok().body(bookService.retrieveAvailableBooks());
    }

    @Override
    public ResponseEntity<List<BookResponse>> getBooksOutOfStock() {
        return ResponseEntity.ok().body(bookService.retrieveBooksOutOfStock());
    }

    @Override
    public ResponseEntity<List<BookResponse>> getBooksByCategory(UUID categoryId) {
        return ResponseEntity.ok().body(bookService.retrieveBooksByCategory(categoryId));
    }

    @Override
    public ResponseEntity<List<BookResponse>> getBooksByAuthor(UUID authorId) {
        return ResponseEntity.ok().body(bookService.retrieveBooksByAuthor(authorId));
    }
}
