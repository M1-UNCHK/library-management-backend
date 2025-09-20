package sn.unchk.librarymanagement.service.notification;

import sn.unchk.librarymanagement.presentation.dto.reponse.NotificationResponse;
import sn.unchk.librarymanagement.presentation.dto.request.NotificationRequest;

import java.util.List;
import java.util.UUID;

public interface NotificationService {
    Boolean save(NotificationRequest request);

    Boolean markAsRead(UUID id);

    List<NotificationResponse> retrieveAllByMember(UUID memberId);

}
