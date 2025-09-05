package sn.unchk.librarymanagement.service.author;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sn.unchk.librarymanagement.domain.exceptions.AlreadyExistsException;
import sn.unchk.librarymanagement.domain.exceptions.MalformedFieldException;
import sn.unchk.librarymanagement.domain.exceptions.NotFoundException;
import sn.unchk.librarymanagement.domain.models.book.Author;
import sn.unchk.librarymanagement.domain.models.book.Book;
import sn.unchk.librarymanagement.presentation.dto.reponse.AuthorResponse;
import sn.unchk.librarymanagement.presentation.dto.request.AuthorRequest;
import sn.unchk.librarymanagement.repository.AuthorRepository;
import sn.unchk.librarymanagement.repository.BookRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private AuthorRequest validAuthorRequest;
    private Author existingAuthor;
    private UUID authorId;

    @BeforeEach
    void setUp() {
        authorId = UUID.randomUUID();
        
        validAuthorRequest = AuthorRequest.builder()
                .name("Chinua Achebe")
                .dateOfBirth(LocalDate.of(1930, 11, 16))
                .biography("Nigerian novelist and poet")
                .build();

        existingAuthor = Author.builder()
                .id(authorId)
                .name("Chinua Achebe")
                .dateOfBirth(LocalDate.of(1930, 11, 16))
                .biography("Nigerian novelist and poet")
                .createdAt(LocalDateTime.now())
                .createdBy("system")
                .build();
    }

    @Test
    void addAuthor_WithValidRequest_ShouldCreateAuthor() {
        // Given
        when(authorRepository.existsByName(validAuthorRequest.getName())).thenReturn(false);
        when(authorRepository.save(any(Author.class))).thenReturn(existingAuthor);

        // When
        Author result = authorService.addAuthor(validAuthorRequest);

        // Then
        assertNotNull(result);
        assertEquals(existingAuthor.getName(), result.getName());
        verify(authorRepository).existsByName(validAuthorRequest.getName());
        verify(authorRepository).save(any(Author.class));
    }

    @Test
    void addAuthor_WithDuplicateName_ShouldThrowAlreadyExistsException() {
        // Given
        when(authorRepository.existsByName(validAuthorRequest.getName())).thenReturn(true);

        // When & Then
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class,
                () -> authorService.addAuthor(validAuthorRequest));
        
        assertEquals("Author already exists ", exception.getMessage());
        verify(authorRepository).existsByName(validAuthorRequest.getName());
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    void addAuthor_WithFutureDateOfBirth_ShouldThrowMalformedFieldException() {
        // Given
        AuthorRequest requestWithFutureDate = AuthorRequest.builder()
                .name("Test Author")
                .dateOfBirth(LocalDate.now().plusDays(1))
                .biography("Test biography")
                .build();
        
        when(authorRepository.existsByName(requestWithFutureDate.getName())).thenReturn(false);

        // When & Then
        MalformedFieldException exception = assertThrows(MalformedFieldException.class,
                () -> authorService.addAuthor(requestWithFutureDate));
        
        assertEquals("Date of birth cannot be in the future", exception.getMessage());
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    void updateAuthor_WithValidRequest_ShouldUpdateAuthor() {
        // Given
        AuthorRequest updateRequest = AuthorRequest.builder()
                .name("Updated Name")
                .dateOfBirth(LocalDate.of(1930, 11, 16))
                .biography("Updated biography")
                .build();
        
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));
        when(authorRepository.existsByNameAndIdIsNot(updateRequest.getName(), authorId)).thenReturn(false);
        when(authorRepository.save(any(Author.class))).thenReturn(existingAuthor);

        // When
        Author result = authorService.updateAuthor(authorId, updateRequest);

        // Then
        assertNotNull(result);
        verify(authorRepository).findById(authorId);
        verify(authorRepository).existsByNameAndIdIsNot(updateRequest.getName(), authorId);
        verify(authorRepository).save(existingAuthor);
    }

    @Test
    void updateAuthor_WithNonExistentId_ShouldThrowNotFoundException() {
        // Given
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> authorService.updateAuthor(authorId, validAuthorRequest));
        
        assertTrue(exception.getMessage().contains("Author with id"));
        verify(authorRepository).findById(authorId);
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    void updateAuthor_WithDuplicateName_ShouldThrowAlreadyExistsException() {
        // Given
        AuthorRequest updateRequest = AuthorRequest.builder()
                .name("Duplicate Name")
                .build();
        
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));
        when(authorRepository.existsByNameAndIdIsNot(updateRequest.getName(), authorId)).thenReturn(true);

        // When & Then
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class,
                () -> authorService.updateAuthor(authorId, updateRequest));
        
        assertEquals("Author already exists ", exception.getMessage());
        verify(authorRepository).findById(authorId);
        verify(authorRepository).existsByNameAndIdIsNot(updateRequest.getName(), authorId);
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    void updateAuthor_WithFutureDateOfBirth_ShouldThrowMalformedFieldException() {
        // Given
        AuthorRequest updateRequest = AuthorRequest.builder()
                .dateOfBirth(LocalDate.now().plusDays(1))
                .build();
        
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));

        // When & Then
        MalformedFieldException exception = assertThrows(MalformedFieldException.class,
                () -> authorService.updateAuthor(authorId, updateRequest));
        
        assertEquals("Date of birth cannot be in the future", exception.getMessage());
        verify(authorRepository).findById(authorId);
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    void deleteAuthor_WithValidId_ShouldDeleteAuthor() {
        // Given
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));
        when(bookRepository.findAllByAuthorId(authorId)).thenReturn(Collections.emptyList());

        // When
        authorService.deleteAuthor(authorId);

        // Then
        verify(authorRepository).findById(authorId);
        verify(bookRepository).findAllByAuthorId(authorId);
        verify(authorRepository).delete(existingAuthor);
    }

    @Test
    void deleteAuthor_WithNonExistentId_ShouldThrowNotFoundException() {
        // Given
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> authorService.deleteAuthor(authorId));
        
        assertTrue(exception.getMessage().contains("Author with id"));
        verify(authorRepository).findById(authorId);
        verify(authorRepository, never()).delete(any(Author.class));
    }

    @Test
    void deleteAuthor_WithLinkedBooks_ShouldThrowMalformedFieldException() {
        // Given
        Book linkedBook = mock(Book.class);
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));
        when(bookRepository.findAllByAuthorId(authorId)).thenReturn(List.of(linkedBook));

        // When & Then
        MalformedFieldException exception = assertThrows(MalformedFieldException.class,
                () -> authorService.deleteAuthor(authorId));
        
        assertEquals("Cannot delete author with linked books", exception.getMessage());
        verify(authorRepository).findById(authorId);
        verify(bookRepository).findAllByAuthorId(authorId);
        verify(authorRepository, never()).delete(any(Author.class));
    }

    @Test
    void retrieveAll_ShouldReturnAllAuthors() {
        // Given
        List<Author> authors = List.of(existingAuthor);
        when(authorRepository.findAll()).thenReturn(authors);

        // When
        List<AuthorResponse> result = authorService.retrieveAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(existingAuthor.getName(), result.get(0).name());
        verify(authorRepository).findAll();
    }

    @Test
    void retrieveAuthorInfo_WithValidId_ShouldReturnAuthor() {
        // Given
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));

        // When
        AuthorResponse result = authorService.retrieveAuthorInfo(authorId);

        // Then
        assertNotNull(result);
        assertEquals(existingAuthor.getName(), result.name());
        assertEquals(existingAuthor.getId(), result.id());
        verify(authorRepository).findById(authorId);
    }

    @Test
    void retrieveAuthorInfo_WithNonExistentId_ShouldThrowNotFoundException() {
        // Given
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> authorService.retrieveAuthorInfo(authorId));
        
        assertTrue(exception.getMessage().contains("Author with id"));
        verify(authorRepository).findById(authorId);
    }
}