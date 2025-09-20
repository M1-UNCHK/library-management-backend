package sn.unchk.librarymanagement.repository;

import io.micrometer.core.instrument.Tags;
import sn.unchk.librarymanagement.domain.models.notification.Notification;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends BaseRepository<Notification> {
    List<Notification> findAllByMemberIdOrderBySendAtDesc(UUID memberId);
}
