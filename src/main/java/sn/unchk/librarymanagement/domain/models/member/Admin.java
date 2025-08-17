package sn.unchk.librarymanagement.domain.models.member;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import static sn.unchk.librarymanagement.constant.GlobalConstant.DEFAULT_PASSWORD;

@Entity(name = "admin")
@Getter @Setter
@AllArgsConstructor
@SuperBuilder
public class Admin extends Member {
    public static Admin create(Admin admin) {
        validateField(admin.getFirstname(), "firstname");
        validateField(admin.getLastname(), "lastname");
        validateField(admin.getUsername(), "username");
        validateField(admin.getEmail(), "password");
        validateField(admin.getEmail(), "email");
        validateField(admin.getAddress(), "address");

        return Admin.builder()
                .firstname(admin.getFirstname())
                .lastname(admin.getLastname())
                .username(admin.getUsername())
                .email(admin.getEmail())
                .phoneNumber(admin.getPhoneNumber())
                .address(admin.getAddress())
                .role(MemberRole.ADMIN)
                .password(admin.getPassword())
                .status(MemberStatus.PENDING)
                .build();
    }
}
