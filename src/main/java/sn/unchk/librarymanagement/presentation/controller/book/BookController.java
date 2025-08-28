package sn.unchk.librarymanagement.presentation.controller.book;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.unchk.librarymanagement.presentation.dto.reponse.BookResponse;
import sn.unchk.librarymanagement.presentation.dto.reponse.HttpResponse;
import sn.unchk.librarymanagement.presentation.dto.request.*;

import java.util.List;
import java.util.UUID;

import static sn.unchk.librarymanagement.constant.GlobalConstant.BOOK_BASE_ROUTE;

@RequestMapping(value = BOOK_BASE_ROUTE)
public interface BookController {

    @PostMapping
    ResponseEntity<HttpResponse> addBook(@RequestBody BookRequest request);

    @PatchMapping("/{id}")
    ResponseEntity<HttpResponse> updateBook(@PathVariable("id") UUID bookId, @RequestBody BookRequest request);

    @GetMapping
    ResponseEntity<List<BookResponse>> getAllBooks();

    @GetMapping("/{id}")
    ResponseEntity<BookResponse> getBookInfo(@PathVariable("id") UUID bookId);

    @GetMapping("/available")
    ResponseEntity<List<BookResponse>> getAvailableBooks();

    @GetMapping("/out-of-stock")
    ResponseEntity<List<BookResponse>> getBooksOutOfStock();

    @GetMapping("/by-category")
    ResponseEntity<List<BookResponse>> getBooksByCategory(@RequestParam("categoryId") UUID categoryId);

    @GetMapping("/by-author")
    ResponseEntity<List<BookResponse>> getBooksByAuthor(@RequestParam("authorId") UUID authorId);
}
