package sn.unchk.librarymanagement.service.category;

import sn.unchk.librarymanagement.domain.models.book.Category;
import sn.unchk.librarymanagement.presentation.dto.reponse.CategoryResponse;
import sn.unchk.librarymanagement.presentation.dto.request.CategoryRequest;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    Category addCategory(CategoryRequest request);
    Category updateCategory(UUID id, CategoryRequest request);
    void deleteCategory(UUID id);
    List<CategoryResponse> retrieveAll();
    CategoryResponse retrieveCategoryInfo(UUID id);
}