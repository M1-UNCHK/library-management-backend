package sn.unchk.librarymanagement.service.author;

import sn.unchk.librarymanagement.domain.models.book.Author;
import sn.unchk.librarymanagement.presentation.dto.reponse.AuthorResponse;
import sn.unchk.librarymanagement.presentation.dto.request.AuthorRequest;

import java.util.List;
import java.util.UUID;

public interface AuthorService {
    Author addAuthor(AuthorRequest request);
    
    Author updateAuthor(UUID id, AuthorRequest request);
    
    void deleteAuthor(UUID id);
    
    List<AuthorResponse> retrieveAll();
    
    AuthorResponse retrieveAuthorInfo(UUID id);
}