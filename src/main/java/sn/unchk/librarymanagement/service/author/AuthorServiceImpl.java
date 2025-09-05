package sn.unchk.librarymanagement.service.author;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.unchk.librarymanagement.domain.exceptions.AlreadyExistsException;
import sn.unchk.librarymanagement.domain.exceptions.MalformedFieldException;
import sn.unchk.librarymanagement.domain.exceptions.NotFoundException;
import sn.unchk.librarymanagement.domain.models.book.Author;
import sn.unchk.librarymanagement.presentation.dto.reponse.AuthorResponse;
import sn.unchk.librarymanagement.presentation.dto.request.AuthorRequest;
import sn.unchk.librarymanagement.repository.AuthorRepository;
import sn.unchk.librarymanagement.repository.BookRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Author addAuthor(AuthorRequest request) {
        if (authorRepository.existsByName(request.getName()))
            throw new AlreadyExistsException("Author");

        if (request.getDateOfBirth().isAfter(LocalDate.now()))
            throw new MalformedFieldException("dateOfBirth", "Date of birth cannot be in the future");

        Author author = Author.add(
                request.getName(),
                request.getDateOfBirth(),
                request.getBiography()
        );

        return authorRepository.save(author);
    }

    @Override
    public Author updateAuthor(UUID id, AuthorRequest request) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("id", String.format("Author with id %s not found", id)));

        if (request.getName() != null && authorRepository.existsByNameAndIdIsNot(request.getName(), id))
            throw new AlreadyExistsException("Author");

        if (request.getDateOfBirth() != null && request.getDateOfBirth().isAfter(LocalDate.now()))
            throw new MalformedFieldException("dateOfBirth", "Date of birth cannot be in the future");

        author.update(request.getName(), request.getDateOfBirth(), request.getBiography());

        return authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(UUID id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("id", String.format("Author with id %s not found", id)));

        if (!bookRepository.findAllByAuthorId(id).isEmpty())
            throw new MalformedFieldException("id", "Cannot delete author with linked books");

        authorRepository.delete(author);
    }

    @Override
    public List<AuthorResponse> retrieveAll() {
        return authorRepository.findAll()
                .stream()
                .map(AuthorResponse::of)
                .toList();
    }

    @Override
    public AuthorResponse retrieveAuthorInfo(UUID id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("id", String.format("Author with id %s not found", id)));

        return AuthorResponse.of(author);
    }
}