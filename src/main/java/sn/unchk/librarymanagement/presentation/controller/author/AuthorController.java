package sn.unchk.librarymanagement.presentation.controller.author;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<HttpResponse> addAuthor(@RequestBody AuthorRequest request);

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<HttpResponse> updateAuthor(@PathVariable("id") UUID id, @RequestBody AuthorRequest request);

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<HttpResponse> deleteAuthor(@PathVariable("id") UUID id);

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','READER')")
    ResponseEntity<List<AuthorResponse>> getAllAuthors();

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READER')")
    ResponseEntity<AuthorResponse> getAuthorInfo(@PathVariable("id") UUID id);
}