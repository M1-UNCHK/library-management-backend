package sn.unchk.librarymanagement.repository;

import sn.unchk.librarymanagement.domain.models.book.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends BaseRepository<Book> {
    boolean existsByName(String name);

    boolean existsByNameAndIdIsNot(String name, UUID id);
    List<Book> findAllByStock(int stock);
    List<Book> findAllByStockIsGreaterThan(int stock);

    List<Book> findAllByCategoryId(UUID categoryId);

    List<Book> findAllByAuthorId(UUID authorId);
}
