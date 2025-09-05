package sn.unchk.librarymanagement.presentation.controller.author;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.unchk.librarymanagement.presentation.dto.reponse.AuthorResponse;
import sn.unchk.librarymanagement.presentation.dto.reponse.HttpResponse;
import sn.unchk.librarymanagement.presentation.dto.request.AuthorRequest;

import java.util.List;
import java.util.UUID;

import static sn.unchk.librarymanagement.constant.GlobalConstant.AUTHOR_BASE_ROUTE;

@RequestMapping(value = AUTHOR_BASE_ROUTE)
public interface AuthorController {

    @PostMapping
    ResponseEntity<HttpResponse> addAuthor(@RequestBody AuthorRequest request);

    @PatchMapping("/{id}")
    ResponseEntity<HttpResponse> updateAuthor(@PathVariable("id") UUID id, @RequestBody AuthorRequest request);

    @DeleteMapping("/{id}")
    ResponseEntity<HttpResponse> deleteAuthor(@PathVariable("id") UUID id);

    @GetMapping
    ResponseEntity<List<AuthorResponse>> getAllAuthors();

    @GetMapping("/{id}")
    ResponseEntity<AuthorResponse> getAuthorInfo(@PathVariable("id") UUID id);
}