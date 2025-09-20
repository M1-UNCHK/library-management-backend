package sn.unchk.librarymanagement.presentation.controller.book;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<HttpResponse> addBook(@RequestBody BookRequest request);

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<HttpResponse> updateBook(@PathVariable("id") UUID bookId, @RequestBody BookRequest request);

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'READER')")
    ResponseEntity<List<BookResponse>> getAllBooks();

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'READER')")
    ResponseEntity<BookResponse> getBookInfo(@PathVariable("id") UUID bookId);

    @GetMapping("/available")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'READER')")
    ResponseEntity<List<BookResponse>> getAvailableBooks();

    @GetMapping("/out-of-stock")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<List<BookResponse>> getBooksOutOfStock();

    @GetMapping("/by-category")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'READER')")
    ResponseEntity<List<BookResponse>> getBooksByCategory(@RequestParam("categoryId") UUID categoryId);

    @GetMapping("/by-author")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'READER')")
    ResponseEntity<List<BookResponse>> getBooksByAuthor(@RequestParam("authorId") UUID authorId);
}
