package sn.unchk.librarymanagement.presentation.controller.notification;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sn.unchk.librarymanagement.presentation.dto.reponse.HttpResponse;
import sn.unchk.librarymanagement.presentation.dto.reponse.NotificationResponse;
import sn.unchk.librarymanagement.service.notification.NotificationService;

import java.util.List;
import java.util.UUID;

import static sn.unchk.librarymanagement.constant.GlobalConstant.UPDATED_MESSAGE;
import static sn.unchk.librarymanagement.constant.GlobalConstant.UPDATED_STATUS_MESSAGE;


@RestController
public class NotificationControllerImpl implements NotificationController{
    private final NotificationService notificationService;

    private static final String ENTITY = "Notification";


    public NotificationControllerImpl(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public ResponseEntity<List<NotificationResponse>> getAllByMember(UUID memberId) {
        return ResponseEntity.ok().body(notificationService.retrieveAllByMember(memberId));
    }

    @Override
    public ResponseEntity<HttpResponse> readNotification(UUID id) {
        notificationService.markAsRead(id);

        return ResponseEntity.ok().body(HttpResponse.success(String.format(UPDATED_MESSAGE, ENTITY)));

    }
}
