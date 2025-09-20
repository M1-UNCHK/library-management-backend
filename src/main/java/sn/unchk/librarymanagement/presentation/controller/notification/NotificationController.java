package sn.unchk.librarymanagement.presentation.controller.notification;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.unchk.librarymanagement.presentation.dto.reponse.HttpResponse;
import sn.unchk.librarymanagement.presentation.dto.reponse.NotificationResponse;
import sn.unchk.librarymanagement.presentation.dto.request.NewPasswordRequest;

import java.util.List;
import java.util.UUID;

import static sn.unchk.librarymanagement.constant.GlobalConstant.NOTIFICATION_BASE_ROUTE;

@RequestMapping(value = NOTIFICATION_BASE_ROUTE)
public interface NotificationController {

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'READER')")
    ResponseEntity<List<NotificationResponse>> getAllByMember(@RequestParam("memberId") UUID memberId);

    @PatchMapping("/{id}/read")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'READER')")
    ResponseEntity<HttpResponse> readNotification(@PathVariable("id") UUID id);
}
