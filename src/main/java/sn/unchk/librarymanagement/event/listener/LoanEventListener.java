package sn.unchk.librarymanagement.event.listener;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import sn.unchk.librarymanagement.event.LoanEvent;
import sn.unchk.librarymanagement.event.EventType;
import sn.unchk.librarymanagement.event.mail.MailNotification;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoanEventListener {
    private final MailNotification mailNotification;

    @EventListener
    public void handleEvent(LoanEvent event) throws MessagingException {
        EventType eventType = event.getEventType();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("lastname", event.getLastname());
        parameters.put("bookName", event.getLoan().getBook().getName());
        parameters.put("author", event.getLoan().getBook().getAuthor().getName());
        parameters.put("category", event.getLoan().getBook().getCategory().getName());
        parameters.put("dueDate", event.getLoan().getDueDate());
        parameters.put("returnedDate", event.getLoan().getReturnedDate());
        mailNotification.sendEmailNotification(eventType, event.getEmail(), parameters);
    }
}
