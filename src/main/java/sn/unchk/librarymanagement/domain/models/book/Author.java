package sn.unchk.librarymanagement.domain.models.book;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import sn.unchk.librarymanagement.domain.exceptions.MalformedFieldException;
import sn.unchk.librarymanagement.domain.models.BaseModel;

import java.time.LocalDate;

import static java.util.Objects.isNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Author extends BaseModel {
    @Column(nullable = false, unique = true)
    private String name;

    private LocalDate dateOfBirth;

    @Size(max = 10000)
    private String biography;

    public static Author add(String name, LocalDate dateOfBirth, String biography) {
        validateField(name, "name");
        validateField(dateOfBirth, "dateOfBirth");
        validateField(biography, "biography");

        if (dateOfBirth.isAfter(LocalDate.now()))
            throw new MalformedFieldException("dateOfBirth", "Date of birth cannot be in the future");

        return Author.builder()
                .name(name)
                .dateOfBirth(dateOfBirth)
                .biography(biography)
                .build();
    }

    public void update(String name, LocalDate dateOfBirth, String biography) {
        if (!isNull(name))
            this.name = name;
        if (!isNull(dateOfBirth)) {
            if (dateOfBirth.isAfter(LocalDate.now()))
                throw new MalformedFieldException("dateOfBirth", "Date of birth cannot be in the future");
            this.dateOfBirth = dateOfBirth;
        }
        if (!isNull(biography))
            this.biography = biography;
    }
}
