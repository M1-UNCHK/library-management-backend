package sn.unchk.librarymanagement.service.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sn.unchk.librarymanagement.domain.exceptions.AlreadyExistsException;
import sn.unchk.librarymanagement.domain.exceptions.MalformedFieldException;
import sn.unchk.librarymanagement.domain.exceptions.NotFoundException;
import sn.unchk.librarymanagement.domain.models.book.Book;
import sn.unchk.librarymanagement.domain.models.book.Category;
import sn.unchk.librarymanagement.presentation.dto.reponse.CategoryResponse;
import sn.unchk.librarymanagement.presentation.dto.request.CategoryRequest;
import sn.unchk.librarymanagement.repository.BookRepository;
import sn.unchk.librarymanagement.repository.CategoryRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private CategoryRequest validCategoryRequest;
    private Category existingCategory;
    private UUID categoryId;

    @BeforeEach
    void setUp() {
        categoryId = UUID.randomUUID();
        
        validCategoryRequest = CategoryRequest.builder()
                .code("FICTION")
                .name("Fiction")
                .description("Fictional literature and novels")
                .build();

        existingCategory = Category.builder()
                .id(categoryId)
                .code("FICTION")
                .name("Fiction")
                .description("Fictional literature and novels")
                .createdAt(LocalDateTime.now())
                .createdBy("system")
                .build();
    }

    @Test
    void addCategory_WithValidRequest_ShouldCreateCategory() {
        // Given
        when(categoryRepository.existsByName(validCategoryRequest.getName())).thenReturn(false);
        when(categoryRepository.existsByCode(validCategoryRequest.getCode())).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(existingCategory);

        // When
        Category result = categoryService.addCategory(validCategoryRequest);

        // Then
        assertNotNull(result);
        assertEquals(existingCategory.getName(), result.getName());
        assertEquals(existingCategory.getCode(), result.getCode());
        verify(categoryRepository).existsByName(validCategoryRequest.getName());
        verify(categoryRepository).existsByCode(validCategoryRequest.getCode());
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void addCategory_WithDuplicateName_ShouldThrowAlreadyExistsException() {
        // Given
        when(categoryRepository.existsByName(validCategoryRequest.getName())).thenReturn(true);

        // When & Then
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class,
                () -> categoryService.addCategory(validCategoryRequest));
        
        assertEquals("Category already exists ", exception.getMessage());
        verify(categoryRepository).existsByName(validCategoryRequest.getName());
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void addCategory_WithDuplicateCode_ShouldThrowAlreadyExistsException() {
        // Given
        when(categoryRepository.existsByName(validCategoryRequest.getName())).thenReturn(false);
        when(categoryRepository.existsByCode(validCategoryRequest.getCode())).thenReturn(true);

        // When & Then
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class,
                () -> categoryService.addCategory(validCategoryRequest));
        
        assertEquals("Category already exists ", exception.getMessage());
        verify(categoryRepository).existsByName(validCategoryRequest.getName());
        verify(categoryRepository).existsByCode(validCategoryRequest.getCode());
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void updateCategory_WithValidRequest_ShouldUpdateCategory() {
        // Given
        CategoryRequest updateRequest = CategoryRequest.builder()
                .code("UPDATED_CODE")
                .name("Updated Name")
                .description("Updated description")
                .build();
        
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.existsByNameAndIdIsNot(updateRequest.getName(), categoryId)).thenReturn(false);
        when(categoryRepository.existsByCodeAndIdIsNot(updateRequest.getCode(), categoryId)).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(existingCategory);

        // When
        Category result = categoryService.updateCategory(categoryId, updateRequest);

        // Then
        assertNotNull(result);
        verify(categoryRepository).findById(categoryId);
        verify(categoryRepository).existsByNameAndIdIsNot(updateRequest.getName(), categoryId);
        verify(categoryRepository).existsByCodeAndIdIsNot(updateRequest.getCode(), categoryId);
        verify(categoryRepository).save(existingCategory);
    }

    @Test
    void updateCategory_WithNonExistentId_ShouldThrowNotFoundException() {
        // Given
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> categoryService.updateCategory(categoryId, validCategoryRequest));
        
        assertTrue(exception.getMessage().contains("Category with id"));
        verify(categoryRepository).findById(categoryId);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void updateCategory_WithDuplicateName_ShouldThrowAlreadyExistsException() {
        // Given
        CategoryRequest updateRequest = CategoryRequest.builder()
                .name("Duplicate Name")
                .build();
        
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.existsByNameAndIdIsNot(updateRequest.getName(), categoryId)).thenReturn(true);

        // When & Then
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class,
                () -> categoryService.updateCategory(categoryId, updateRequest));
        
        assertEquals("Category already exists ", exception.getMessage());
        verify(categoryRepository).findById(categoryId);
        verify(categoryRepository).existsByNameAndIdIsNot(updateRequest.getName(), categoryId);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void updateCategory_WithDuplicateCode_ShouldThrowAlreadyExistsException() {
        // Given
        CategoryRequest updateRequest = CategoryRequest.builder()
                .code("DUPLICATE_CODE")
                .build();
        
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.existsByCodeAndIdIsNot(updateRequest.getCode(), categoryId)).thenReturn(true);

        // When & Then
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class,
                () -> categoryService.updateCategory(categoryId, updateRequest));
        
        assertEquals("Category already exists ", exception.getMessage());
        verify(categoryRepository).findById(categoryId);
        verify(categoryRepository).existsByCodeAndIdIsNot(updateRequest.getCode(), categoryId);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void deleteCategory_WithValidId_ShouldDeleteCategory() {
        // Given
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(bookRepository.findAllByCategoryId(categoryId)).thenReturn(Collections.emptyList());

        // When
        categoryService.deleteCategory(categoryId);

        // Then
        verify(categoryRepository).findById(categoryId);
        verify(bookRepository).findAllByCategoryId(categoryId);
        verify(categoryRepository).delete(existingCategory);
    }

    @Test
    void deleteCategory_WithNonExistentId_ShouldThrowNotFoundException() {
        // Given
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> categoryService.deleteCategory(categoryId));
        
        assertTrue(exception.getMessage().contains("Category with id"));
        verify(categoryRepository).findById(categoryId);
        verify(categoryRepository, never()).delete(any(Category.class));
    }

    @Test
    void deleteCategory_WithLinkedBooks_ShouldThrowMalformedFieldException() {
        // Given
        Book linkedBook = mock(Book.class);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(bookRepository.findAllByCategoryId(categoryId)).thenReturn(List.of(linkedBook));

        // When & Then
        MalformedFieldException exception = assertThrows(MalformedFieldException.class,
                () -> categoryService.deleteCategory(categoryId));
        
        assertEquals("Cannot delete category with linked books", exception.getMessage());
        verify(categoryRepository).findById(categoryId);
        verify(bookRepository).findAllByCategoryId(categoryId);
        verify(categoryRepository, never()).delete(any(Category.class));
    }

    @Test
    void retrieveAll_ShouldReturnAllCategories() {
        // Given
        List<Category> categories = List.of(existingCategory);
        when(categoryRepository.findAll()).thenReturn(categories);

        // When
        List<CategoryResponse> result = categoryService.retrieveAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(existingCategory.getName(), result.get(0).name());
        assertEquals(existingCategory.getCode(), result.get(0).code());
        verify(categoryRepository).findAll();
    }

    @Test
    void retrieveCategoryInfo_WithValidId_ShouldReturnCategory() {
        // Given
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));

        // When
        CategoryResponse result = categoryService.retrieveCategoryInfo(categoryId);

        // Then
        assertNotNull(result);
        assertEquals(existingCategory.getId(), result.id());
        assertEquals(existingCategory.getName(), result.name());
        assertEquals(existingCategory.getCode(), result.code());
        verify(categoryRepository).findById(categoryId);
    }

    @Test
    void retrieveCategoryInfo_WithNonExistentId_ShouldThrowNotFoundException() {
        // Given
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> categoryService.retrieveCategoryInfo(categoryId));
        
        assertTrue(exception.getMessage().contains("Category with id"));
        verify(categoryRepository).findById(categoryId);
    }
}