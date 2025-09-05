package sn.unchk.librarymanagement.domain.models.book;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sn.unchk.librarymanagement.domain.models.BaseModel;

import static java.util.Objects.isNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Category extends BaseModel {
    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 10000)
    private String description;

    public static Category add(String code, String name, String description) {
        validateField(code, "code");
        validateField(name, "name");
        validateField(description, "description");

        return Category.builder()
                .code(code)
                .name(name)
                .description(description)
                .build();
    }

    public void update(String code, String name, String description) {
        if (!isNull(code))
            this.code = code;
        if (!isNull(name))
            this.name = name;
        if (!isNull(description))
            this.description = description;
    }
}
