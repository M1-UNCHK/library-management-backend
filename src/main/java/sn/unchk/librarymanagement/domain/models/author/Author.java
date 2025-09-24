package sn.unchk.librarymanagement.domain.models.author;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import sn.unchk.librarymanagement.domain.exceptions.MalformedFieldException;
import sn.unchk.librarymanagement.domain.models.BaseModel;
import sn.unchk.librarymanagement.domain.validation.Create;
import sn.unchk.librarymanagement.domain.validation.Update;

import java.time.LocalDate;

import static java.util.Objects.isNull;
import static sn.unchk.librarymanagement.constant.GlobalConstant.REQUIRED_FIELD_NAME;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Author extends BaseModel {
    @Column(nullable = false, unique = true)
    @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class })
    private String name;

    private LocalDate dateOfBirth;

    @Size(max = 10000)
    private String biography;

    public static Author add(String name, LocalDate dateOfBirth, String biography) {
        validateField(name, "name");
        validateField(dateOfBirth, "dateOfBirth");
        validateField(biography, "biography");

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
