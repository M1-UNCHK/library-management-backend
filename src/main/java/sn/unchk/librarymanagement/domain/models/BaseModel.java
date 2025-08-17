package sn.unchk.librarymanagement.domain.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sn.unchk.librarymanagement.domain.exceptions.MalformedFieldException;
import sn.unchk.librarymanagement.domain.exceptions.Pattern;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Objects.isNull;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@SuperBuilder
public abstract class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreatedDate
    @JsonFormat(pattern = Pattern.DATE)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonFormat(pattern = Pattern.DATE)
    private LocalDateTime updatedAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    protected static void validateField(Object field, String fieldName) {
        if (isNull(field)) {
            throw new MalformedFieldException(fieldName, fieldName.concat(" cannot be null"));
        }
    }
}