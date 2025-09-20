package sn.unchk.librarymanagement.service.notification;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.unchk.librarymanagement.domain.exceptions.NotFoundException;
import sn.unchk.librarymanagement.domain.models.member.Member;
import sn.unchk.librarymanagement.domain.models.notification.Notification;
import sn.unchk.librarymanagement.presentation.dto.reponse.NotificationResponse;
import sn.unchk.librarymanagement.presentation.dto.request.NotificationRequest;
import sn.unchk.librarymanagement.repository.MemberRepository;
import sn.unchk.librarymanagement.repository.NotificationRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService{
    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository, MemberRepository memberRepository) {
        this.notificationRepository = notificationRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public Boolean save(NotificationRequest request) {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new NotFoundException("member", String.format("Member with ID %s not found", request.memberId())));

        Notification notification = Notification.create(
                request.title(),
                request.content(),
                request.type(),
                member
        );

        notificationRepository.save(notification);

        return true;
    }

    @Override
    public Boolean markAsRead(UUID id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("notification", String.format("Notification with ID %s not found", id)));

        notification.read();

        notificationRepository.save(notification);

        return true;
    }

    @Override
    public List<NotificationResponse> retrieveAllByMember(UUID memberId) {
        return notificationRepository.findAllByMemberIdOrderBySendAtDesc(memberId)
                .stream()
                .map(NotificationResponse::of)
                .toList();
    }
}
