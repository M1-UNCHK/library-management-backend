package sn.unchk.librarymanagement.presentation.controller.category;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import sn.unchk.librarymanagement.domain.validation.Create;
import sn.unchk.librarymanagement.domain.validation.Update;
import sn.unchk.librarymanagement.presentation.dto.reponse.CategoryResponse;
import sn.unchk.librarymanagement.presentation.dto.reponse.HttpResponse;
import sn.unchk.librarymanagement.presentation.dto.request.CategoryRequest;
import sn.unchk.librarymanagement.presentation.validation.RequestValidator;
import sn.unchk.librarymanagement.service.category.CategoryService;

import java.util.List;
import java.util.UUID;

import static sn.unchk.librarymanagement.constant.GlobalConstant.*;

@RestController
public class CategoryControllerImpl implements CategoryController {
    private static final String ENTITY = "Category";

    private final CategoryService categoryService;
    private final RequestValidator validator;

    public CategoryControllerImpl(CategoryService categoryService, RequestValidator validator) {
        this.categoryService = categoryService;
        this.validator = validator;
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HttpResponse> addCategory(CategoryRequest request) {
        validator.assertValidity(request, Create.class);

        categoryService.addCategory(request);

        return ResponseEntity.ok().body(HttpResponse.success(String.format(CREATED_MESSAGE, ENTITY)));
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HttpResponse> updateCategory(UUID categoryId, CategoryRequest request) {
        validator.assertValidity(request, Update.class);

        categoryService.updateCategory(categoryId, request);

        return ResponseEntity.ok().body(HttpResponse.success(String.format(UPDATED_MESSAGE, ENTITY)));
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HttpResponse> deleteCategory(UUID categoryId) {
        categoryService.deleteCategory(categoryId);

        return ResponseEntity.ok().body(HttpResponse.success(String.format(DELETED_MESSAGE, ENTITY)));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN','READER')")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok().body(categoryService.retrieveAll());
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN','READER')")
    public ResponseEntity<CategoryResponse> getCategoryInfo(UUID categoryId) {
        return ResponseEntity.ok().body(categoryService.retrieveCategoryInfo(categoryId));
    }
}