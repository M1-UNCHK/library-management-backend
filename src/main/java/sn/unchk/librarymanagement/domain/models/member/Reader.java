package sn.unchk.librarymanagement.domain.models.member;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static sn.unchk.librarymanagement.constant.GlobalConstant.DEFAULT_PASSWORD;

@Entity
@Getter @Setter
@SuperBuilder
@AllArgsConstructor
public class Reader extends Member{
    public static Reader create(Reader reader) {
        validateField(reader.getFirstname(), "firstname");
        validateField(reader.getLastname(), "lastname");
        validateField(reader.getUsername(), "username");
        validateField(reader.getEmail(), "password");
        validateField(reader.getEmail(), "email");
        validateField(reader.getAddress(), "address");

        return Reader.builder()
                .firstname(reader.getFirstname())
                .lastname(reader.getLastname())
                .username(reader.getUsername())
                .email(reader.getEmail())
                .phoneNumber(reader.getPhoneNumber())
                .address(reader.getAddress())
                .role(MemberRole.READER)
                .password(reader.getPassword())
                .status(MemberStatus.PENDING)
                .build();
    }
}
