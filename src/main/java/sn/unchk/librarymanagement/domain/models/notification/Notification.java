package sn.unchk.librarymanagement.domain.models.notification;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import sn.unchk.librarymanagement.domain.models.BaseModel;
import sn.unchk.librarymanagement.domain.models.member.Member;
import sn.unchk.librarymanagement.domain.validation.Create;
import sn.unchk.librarymanagement.domain.validation.Update;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static sn.unchk.librarymanagement.constant.GlobalConstant.REQUIRED_FIELD_NAME;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Notification extends BaseModel {
    @Column(nullable = false)
    @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class })
    private String title;

    @Column(nullable = false)
    @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class })
    private String content;

    @Column(nullable = false)
    @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class })
    private LocalDateTime sendAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class })
    private NotificationType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class })
    private NotificationStatus status;

    @ManyToOne(optional = false)
    @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class })
    private Member member;

    public static Notification create(String title, String content, NotificationType type, Member member) {
        validateField(title, "title");
        validateField(content, "content");
        validateField(type, "type");
        validateField(member.getId(), "member");

        return Notification.builder()
                .title(title)
                .content(content)
                .type(type)
                .member(member)
                .status(NotificationStatus.NOT_READ)
                .sendAt(LocalDateTime.now())
                .build();
    }


    public void read() {
        this.status = NotificationStatus.READ;
    }
}
