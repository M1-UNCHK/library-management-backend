package sn.unchk.librarymanagement.presentation.controller.author;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import sn.unchk.librarymanagement.domain.validation.Create;
import sn.unchk.librarymanagement.domain.validation.Update;
import sn.unchk.librarymanagement.presentation.dto.reponse.AuthorResponse;
import sn.unchk.librarymanagement.presentation.dto.reponse.HttpResponse;
import sn.unchk.librarymanagement.presentation.dto.request.AuthorRequest;
import sn.unchk.librarymanagement.presentation.validation.RequestValidator;
import sn.unchk.librarymanagement.service.author.AuthorService;

import java.util.List;
import java.util.UUID;

import static sn.unchk.librarymanagement.constant.GlobalConstant.*;

@RestController
public class AuthorControllerImpl implements AuthorController {
    private static final String ENTITY = "Author";

    private final AuthorService authorService;
    private final RequestValidator validator;

    public AuthorControllerImpl(AuthorService authorService, RequestValidator validator) {
        this.authorService = authorService;
        this.validator = validator;
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HttpResponse> addAuthor(AuthorRequest request) {
        validator.assertValidity(request, Create.class);

        authorService.addAuthor(request);

        return ResponseEntity.ok().body(HttpResponse.success(String.format(CREATED_MESSAGE, ENTITY)));
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HttpResponse> updateAuthor(UUID id, AuthorRequest request) {
        validator.assertValidity(request, Update.class);

        authorService.updateAuthor(id, request);

        return ResponseEntity.ok().body(HttpResponse.success(String.format(UPDATED_MESSAGE, ENTITY)));
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HttpResponse> deleteAuthor(UUID id) {
        authorService.deleteAuthor(id);

        return ResponseEntity.ok().body(HttpResponse.success(String.format(DELETED_MESSAGE, ENTITY)));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN','READER')")
    public ResponseEntity<List<AuthorResponse>> getAllAuthors() {
        return ResponseEntity.ok().body(authorService.retrieveAll());
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN','READER')")
    public ResponseEntity<AuthorResponse> getAuthorInfo(UUID id) {
        return ResponseEntity.ok().body(authorService.retrieveAuthorInfo(id));
    }
}