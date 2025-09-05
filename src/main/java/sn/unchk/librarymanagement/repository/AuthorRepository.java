package sn.unchk.librarymanagement.repository;

import sn.unchk.librarymanagement.domain.models.book.Author;

import java.util.UUID;

public interface AuthorRepository extends BaseRepository<Author> {
    boolean existsByName(String name);
    
    boolean existsByNameAndIdIsNot(String name, UUID id);
}
