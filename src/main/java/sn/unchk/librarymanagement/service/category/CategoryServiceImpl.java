package sn.unchk.librarymanagement.service.category;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.unchk.librarymanagement.domain.exceptions.AlreadyExistsException;
import sn.unchk.librarymanagement.domain.exceptions.MalformedFieldException;
import sn.unchk.librarymanagement.domain.exceptions.NotFoundException;
import sn.unchk.librarymanagement.domain.models.book.Category;
import sn.unchk.librarymanagement.presentation.dto.reponse.CategoryResponse;
import sn.unchk.librarymanagement.presentation.dto.request.CategoryRequest;
import sn.unchk.librarymanagement.repository.BookRepository;
import sn.unchk.librarymanagement.repository.CategoryRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, BookRepository bookRepository) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Category addCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName()))
            throw new AlreadyExistsException("Category");

        if (categoryRepository.existsByCode(request.getCode()))
            throw new AlreadyExistsException("Category");

        Category category = Category.add(
                request.getCode(),
                request.getName(),
                request.getDescription()
        );

        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(UUID id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("id", String.format("Category with id %s not found", id)));

        if (request.getName() != null && categoryRepository.existsByNameAndIdIsNot(request.getName(), id))
            throw new AlreadyExistsException("Category");

        if (request.getCode() != null && categoryRepository.existsByCodeAndIdIsNot(request.getCode(), id))
            throw new AlreadyExistsException("Category");

        category.update(request.getCode(), request.getName(), request.getDescription());

        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("id", String.format("Category with id %s not found", id)));

        // Check if any books reference this category
        if (!bookRepository.findAllByCategoryId(id).isEmpty()) {
            throw new MalformedFieldException("id", "Cannot delete category with linked books");
        }

        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryResponse> retrieveAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::of)
                .toList();
    }

    @Override
    public CategoryResponse retrieveCategoryInfo(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("id", String.format("Category with id %s not found", id)));

        return CategoryResponse.of(category);
    }
}