package sn.unchk.librarymanagement.domain.models.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sn.unchk.librarymanagement.domain.models.BaseModel;
import sn.unchk.librarymanagement.domain.validation.Create;
import sn.unchk.librarymanagement.domain.validation.Update;

import static sn.unchk.librarymanagement.constant.GlobalConstant.REQUIRED_FIELD_NAME;
import static sn.unchk.librarymanagement.domain.exceptions.Pattern.EMAIL;
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@SuperBuilder
public abstract class Member extends BaseModel {
    @Column(nullable = false)
    @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class })
    private String firstname;

    @Column(nullable = false)
    @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class })
    private String lastname;


    @Column(nullable = false, unique = true)
    @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class })
    private String username;

    @Column(nullable = false, unique = true)
    @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class })
    @Pattern(regexp = EMAIL)
    private String email;


    @Column(nullable = false, unique = true)
    @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class })
    private String phoneNumber;

    @Column(nullable = false)
    @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class })
    private String address;


    @Column(nullable = false)
    @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class })
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Column(nullable = false)
    @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class })
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class })
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    public void changeStatus(MemberStatus status) {
        if (this.status.equals(status))
            return;
        this.status = status;
    }

    public void update(Member member) {
        firstname = member.getFirstname();
        lastname = member.getLastname();
        email = member.getEmail();
        phoneNumber = member.getPhoneNumber();
        address = member.getAddress();
    }
    public boolean isActive() {
        return status == MemberStatus.ACTIVE;
    }

    public void changePassword(String password) {
        validateField("password", password);
        this.password = password;
    }
}

