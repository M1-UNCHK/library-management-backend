package sn.unchk.librarymanagement.domain.models.book;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sn.unchk.librarymanagement.domain.exceptions.MalformedFieldException;
import sn.unchk.librarymanagement.domain.models.BaseModel;
import sn.unchk.librarymanagement.domain.models.author.Author;
import sn.unchk.librarymanagement.domain.models.category.Category;

import java.time.LocalDate;

import static java.util.Objects.isNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Book extends BaseModel {
    @Column(nullable = false, unique = true)
    private String name;

    private LocalDate publicationDate;

    @Column(nullable = false)
    private int stock;

    @ManyToOne(optional = false)
    private Author author;

    @ManyToOne(optional = false)
    private Category category;

    public static Book add(String name, LocalDate publicationDate, int stock, Author author, Category category) {
        validateField(name, "name");
        validateField(publicationDate, "publicationDate");
        validateField(author.getId(), "author");
        validateField(category.getId(), "category");

        if(publicationDate.isAfter(LocalDate.now()))
            throw new MalformedFieldException("publicationDate", "Publication Date cannot be after today");

        return Book.builder()
                .name(name)
                .publicationDate(publicationDate)
                .stock(stock)
                .author(author)
                .category(category)
                .build();
    }

    public void update(String name, LocalDate publicationDate, Integer stock, Author author, Category category) {
        if (!isNull(name))
            this.name = name;
        if (!isNull(stock))
            this.stock = stock;
        if(!isNull(publicationDate)) {
            if(publicationDate.isAfter(LocalDate.now()))
                throw new MalformedFieldException("publicationDate", "Publication Date cannot be after today");
            this.publicationDate = publicationDate;
        }
        if (!isNull(author))
            this.author = author;
        if (!isNull(category))
            this.category = category;
    }

    public void increaseStock(int stock) {
        this.stock += stock;
    }

    public void decreaseStock(int stock) {
        this.stock -= stock;
    }

    public boolean hasAvailableStock() {
        return this.stock > 0;
    }
}
