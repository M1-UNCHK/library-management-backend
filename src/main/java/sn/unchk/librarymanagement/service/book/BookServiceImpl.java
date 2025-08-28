package sn.unchk.librarymanagement.service.book;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import sn.unchk.librarymanagement.domain.exceptions.AlreadyExistsException;
import sn.unchk.librarymanagement.domain.exceptions.MalformedFieldException;
import sn.unchk.librarymanagement.domain.exceptions.NotFoundException;
import sn.unchk.librarymanagement.domain.models.book.Author;
import sn.unchk.librarymanagement.domain.models.book.Book;
import sn.unchk.librarymanagement.domain.models.book.Category;
import sn.unchk.librarymanagement.presentation.dto.reponse.BookResponse;
import sn.unchk.librarymanagement.presentation.dto.request.BookRequest;
import sn.unchk.librarymanagement.repository.AuthorRepository;
import sn.unchk.librarymanagement.repository.BookRepository;
import sn.unchk.librarymanagement.repository.CategoryRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;

@Service
@Transactional
public class BookServiceImpl implements BookService{
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;

    public BookServiceImpl(AuthorRepository authorRepository, CategoryRepository categoryRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Book addBook(BookRequest request) {
        if (bookRepository.existsByName(request.getName()))
            throw new AlreadyExistsException("Book");

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new NotFoundException("authorId", String.format("Category with id %s not found", request.getAuthorId())));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException("categoryId", String.format("Author with id %s not found", request.getAuthorId())));

        Book book = Book.add(
                request.getName(),
                request.getPublicationDate(),
                request.getStock(),
                author,
                category
        );

        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(UUID id, BookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("id", String.format("Book with id %s not found", id)));

        if (bookRepository.existsByNameAndIdIsNot(request.getName(), book.getId()))
            throw new AlreadyExistsException("Book");

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new NotFoundException("authorId", String.format("Category with id %s not found", request.getAuthorId())));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException("categoryId", String.format("Author with id %s not found", request.getAuthorId())));

        book.update(request.getName(), request.getPublicationDate(), request.getStock(), author, category);

        return bookRepository.save(book);
    }

    @Override
    public List<BookResponse> retrieveAll() {
        List<Book> books = bookRepository.findAll();

        return books.stream().map(BookResponse::of).toList();
    }

    @Override
    public BookResponse retrieveBookInfo(UUID id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("id", String.format("Book with id %s not found", id)));

        return BookResponse.of(book);
    }

    @Override
    public List<BookResponse> retrieveBooksOutOfStock() {
        List<Book> books = bookRepository.findAllByStock(0);

        return books.stream().map(BookResponse::of).toList();
    }

    @Override
    public List<BookResponse> retrieveAvailableBooks() {
        List<Book> books = bookRepository.findAllByStockIsGreaterThan(0);

        return books.stream().map(BookResponse::of).toList();
    }

    @Override
    public List<BookResponse> retrieveBooksByCategory(UUID categoryId) {
        List<Book> books = bookRepository.findAllByCategoryId(categoryId);

        return books.stream().map(BookResponse::of).toList();
    }

    @Override
    public List<BookResponse> retrieveBooksByAuthor(UUID authorId) {
        List<Book> books = bookRepository.findAllByAuthorId(authorId);

        return books.stream().map(BookResponse::of).toList();
    }
}
