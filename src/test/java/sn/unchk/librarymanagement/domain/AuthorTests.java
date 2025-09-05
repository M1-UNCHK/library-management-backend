package sn.unchk.librarymanagement.domain;

import org.junit.jupiter.api.Test;
import sn.unchk.librarymanagement.domain.exceptions.MalformedFieldException;
import sn.unchk.librarymanagement.domain.models.book.Author;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorTests {

    @Test
    void add_WithValidParameters_ShouldCreateAuthor() {
        // Given
        String name = "Chinua Achebe";
        LocalDate dateOfBirth = LocalDate.of(1930, 11, 16);
        String biography = "Nigerian novelist and poet";

        // When
        Author author = Author.add(name, dateOfBirth, biography);

        // Then
        assertNotNull(author);
        assertEquals(name, author.getName());
        assertEquals(dateOfBirth, author.getDateOfBirth());
        assertEquals(biography, author.getBiography());
    }

    @Test
    void add_WithFutureDateOfBirth_ShouldThrowMalformedFieldException() {
        // Given
        String name = "Test Author";
        LocalDate futureDateOfBirth = LocalDate.now().plusDays(1);
        String biography = "Test biography";

        // When & Then
        MalformedFieldException exception = assertThrows(MalformedFieldException.class,
                () -> Author.add(name, futureDateOfBirth, biography));
        
        assertEquals("Date of birth cannot be in the future", exception.getMessage());
    }

    @Test
    void update_WithValidParameters_ShouldUpdateAuthor() {
        // Given
        Author author = Author.builder()
                .name("Original Name")
                .dateOfBirth(LocalDate.of(1930, 11, 16))
                .biography("Original biography")
                .build();
        
        String newName = "Updated Name";
        LocalDate newDateOfBirth = LocalDate.of(1931, 12, 17);
        String newBiography = "Updated biography";

        // When
        author.update(newName, newDateOfBirth, newBiography);

        // Then
        assertEquals(newName, author.getName());
        assertEquals(newDateOfBirth, author.getDateOfBirth());
        assertEquals(newBiography, author.getBiography());
    }

    @Test
    void update_WithNullParameters_ShouldKeepOriginalValues() {
        // Given
        String originalName = "Original Name";
        LocalDate originalDateOfBirth = LocalDate.of(1930, 11, 16);
        String originalBiography = "Original biography";
        
        Author author = Author.builder()
                .name(originalName)
                .dateOfBirth(originalDateOfBirth)
                .biography(originalBiography)
                .build();

        // When
        author.update(null, null, null);

        // Then
        assertEquals(originalName, author.getName());
        assertEquals(originalDateOfBirth, author.getDateOfBirth());
        assertEquals(originalBiography, author.getBiography());
    }

    @Test
    void update_WithFutureDateOfBirth_ShouldThrowMalformedFieldException() {
        // Given
        Author author = Author.builder()
                .name("Test Author")
                .dateOfBirth(LocalDate.of(1930, 11, 16))
                .biography("Test biography")
                .build();
        
        LocalDate futureDateOfBirth = LocalDate.now().plusDays(1);

        // When & Then
        MalformedFieldException exception = assertThrows(MalformedFieldException.class,
                () -> author.update(null, futureDateOfBirth, null));
        
        assertEquals("Date of birth cannot be in the future", exception.getMessage());
    }
}