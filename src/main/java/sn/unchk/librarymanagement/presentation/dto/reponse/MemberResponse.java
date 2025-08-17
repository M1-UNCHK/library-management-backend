package sn.unchk.librarymanagement.presentation.dto.reponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sn.unchk.librarymanagement.domain.exceptions.Pattern;
import sn.unchk.librarymanagement.domain.models.member.Member;
import sn.unchk.librarymanagement.domain.models.member.MemberRole;
import sn.unchk.librarymanagement.domain.models.member.MemberStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberResponse {
    private UUID id;

    private String firstname;

    private String lastname;

    private String username;

    private String email;

    private String phoneNumber;

    private String address;

    private MemberRole role;
    private MemberStatus status;

    @JsonFormat(pattern = Pattern.DATE)
    public LocalDateTime createdAt;

    @JsonFormat(pattern = Pattern.DATE)
    public LocalDateTime updatedAt;

    public String createdBy;
    public String updatedBy;

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .firstname(member.getFirstname())
                .lastname(member.getLastname())
                .username(member.getUsername())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .address(member.getAddress())
                .role(member.getRole())
                .status(member.getStatus())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .createdBy(member.getCreatedBy())
                .updatedBy(member.getUpdatedBy())
                .build();
    }
}
