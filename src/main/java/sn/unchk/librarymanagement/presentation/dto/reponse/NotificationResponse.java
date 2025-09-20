package sn.unchk.librarymanagement.presentation.dto.reponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import sn.unchk.librarymanagement.domain.models.notification.Notification;
import sn.unchk.librarymanagement.domain.models.notification.NotificationStatus;
import sn.unchk.librarymanagement.domain.models.notification.NotificationType;
import sn.unchk.librarymanagement.domain.validation.Pattern;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationResponse(
        UUID id,
        String title,
        String content,
        NotificationType type,
        NotificationStatus status,
        @JsonFormat(pattern = Pattern.DATE)
        LocalDateTime sendAt,
        MemberResponse memberResponse
) {
    public static NotificationResponse of(Notification notification) {
        return new NotificationResponse(
                notification.getId(),
                notification.getTitle(),
                notification.getContent(),
                notification.getType(),
                notification.getStatus(),
                notification.getSendAt(),
                MemberResponse.of(notification.getMember())
        );
    }
}
