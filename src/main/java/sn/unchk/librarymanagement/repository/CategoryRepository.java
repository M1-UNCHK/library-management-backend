package sn.unchk.librarymanagement.repository;

import sn.unchk.librarymanagement.domain.models.book.Category;

import java.util.UUID;

public interface CategoryRepository extends BaseRepository<Category> {
    boolean existsByName(String name);
    boolean existsByNameAndIdIsNot(String name, UUID id);
    boolean existsByCode(String code);
    boolean existsByCodeAndIdIsNot(String code, UUID id);
}
