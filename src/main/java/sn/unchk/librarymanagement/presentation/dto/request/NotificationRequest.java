package sn.unchk.librarymanagement.presentation.dto.request;

import sn.unchk.librarymanagement.domain.models.notification.NotificationType;

import java.util.UUID;

public record NotificationRequest(
       String title,
       String content,
       UUID memberId,
       NotificationType type
) {
  
}
