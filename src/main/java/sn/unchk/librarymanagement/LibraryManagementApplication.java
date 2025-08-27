package sn.unchk.librarymanagement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import sn.unchk.librarymanagement.domain.models.book.Author;
import sn.unchk.librarymanagement.domain.models.book.Book;
import sn.unchk.librarymanagement.domain.models.book.Category;
import sn.unchk.librarymanagement.domain.models.member.MemberRole;
import sn.unchk.librarymanagement.presentation.dto.request.MemberRequest;
import sn.unchk.librarymanagement.presentation.security.RsaKeyProperties;
import sn.unchk.librarymanagement.presentation.security.SecurityConfigProperties;
import sn.unchk.librarymanagement.presentation.security.token.TokenProperties;
import sn.unchk.librarymanagement.repository.AuthorRepository;
import sn.unchk.librarymanagement.repository.BookRepository;
import sn.unchk.librarymanagement.repository.CategoryRepository;
import sn.unchk.librarymanagement.service.member.MemberService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableConfigurationProperties({RsaKeyProperties.class, TokenProperties.class, SecurityConfigProperties.class})
@Slf4j
public class LibraryManagementApplication {
    public static final String ADMIN_USERNAME = "diack";
    public static final String ADMIN_PASSWORD = "diack@123";
    public static final String ADMIN_EMAIL = "m.diackk@gmail.com";
    public static final String READER_USERNAME = "beni";
    public static final String READER_PASSWORD = "beni@123";
    public static final String READER_EMAIL = "djongnabeb@gmail.com";

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementApplication.class, args);
    }
    @Bean
    CommandLineRunner runner(MemberService memberService,
                             AuthorRepository authorRepository,
                             CategoryRepository categoryRepository,
                             BookRepository bookRepository) {
        return args -> {
            if (memberService.retrieveAllMembers().isEmpty()) {
                saveMembers(memberService);
                log.info("MEMBERS SAVED SUCCESSFUL");
            }

            if (authorRepository.count() == 0) {
                savaAuthors(authorRepository);
                log.info("AUTHORS SAVED SUCCESSFUL");
            }

            if (categoryRepository.count() == 0) {
                saveCategories(categoryRepository);
                log.info("CATEGORIES SAVED SUCCESSFUL");
            }
            if (bookRepository.count() == 0) {
                saveBooks(authorRepository, categoryRepository, bookRepository);
                log.info("BOOKS SAVED SUCCESSFUL");
            }
        };
    }

    private void saveMembers(MemberService memberService) {
        MemberRequest admin = MemberRequest.builder()
                .firstname("Mouhamad")
                .lastname("DIACK")
                .username(ADMIN_USERNAME)
                .email(ADMIN_EMAIL)
                .password(ADMIN_PASSWORD)
                .address("Dakar")
                .phoneNumber("780010101")
                .role(MemberRole.ADMIN)
                .build();

        memberService.createAdmin(admin);

        MemberRequest reader = MemberRequest.builder()
                .firstname("Djongnabe")
                .lastname("Beni")
                .username(READER_USERNAME)
                .email(READER_EMAIL)
                .password(READER_PASSWORD)
                .address("Dakar")
                .phoneNumber("780010102")
                .role(MemberRole.READER)
                .build();

        memberService.createReader(reader);
    }

    private void savaAuthors(AuthorRepository authorRepository) {
        Author author = Author.builder()
                .name("Mariama Ba")
                .biography("Mariama Bâ, née le 17 avril 1929 à Dakar et morte dans la même ville le 17 août 1981, est une femme de lettres sénégalaise. Elle est issue d'une famille musulmane de l'ethnie Lébous. Dans son œuvre appelée Une si longue lettre, elle critique les inégalités entre hommes et femmes dues à la tradition africaine.")
                .dateOfBirth(LocalDate.now().minusYears(70))
                .createdBy(ADMIN_USERNAME)
                .updatedBy(ADMIN_USERNAME)
                .build();

        authorRepository.save(author);
    }

    private void saveCategories(CategoryRepository categoryRepository) {
        Category category =  Category.builder()
                .code("CTR-1")
                .name("Roman")
                .createdBy(ADMIN_USERNAME)
                .updatedBy(ADMIN_USERNAME)
                .build();

        categoryRepository.save(category);
    }
    private void saveBooks(AuthorRepository authorRepository, CategoryRepository categoryRepository, BookRepository bookRepository) {
        Author author = authorRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow();

        Category category = categoryRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow();

        Book book = Book.builder()
                .name("Une si longue lettre")
                .publicationDate(LocalDate.now().minusYears(30))
                .stock(50)
                .author(author)
                .category(category)
                .createdBy(ADMIN_USERNAME)
                .updatedBy(ADMIN_USERNAME)
                .build();

        bookRepository.save(book);
    }
}
