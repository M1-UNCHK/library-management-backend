package sn.unchk.librarymanagement.presentation.controller.category;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.unchk.librarymanagement.presentation.dto.reponse.CategoryResponse;
import sn.unchk.librarymanagement.presentation.dto.reponse.HttpResponse;
import sn.unchk.librarymanagement.presentation.dto.request.CategoryRequest;

import java.util.List;
import java.util.UUID;

import static sn.unchk.librarymanagement.constant.GlobalConstant.CATEGORY_BASE_ROUTE;

@RequestMapping(value = CATEGORY_BASE_ROUTE)
public interface CategoryController {

    @PostMapping
    ResponseEntity<HttpResponse> addCategory(@RequestBody CategoryRequest request);

    @PatchMapping("/{id}")
    ResponseEntity<HttpResponse> updateCategory(@PathVariable("id") UUID categoryId, @RequestBody CategoryRequest request);

    @DeleteMapping("/{id}")
    ResponseEntity<HttpResponse> deleteCategory(@PathVariable("id") UUID categoryId);

    @GetMapping
    ResponseEntity<List<CategoryResponse>> getAllCategories();

    @GetMapping("/{id}")
    ResponseEntity<CategoryResponse> getCategoryInfo(@PathVariable("id") UUID categoryId);
}