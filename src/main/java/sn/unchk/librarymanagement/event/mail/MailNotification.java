package sn.unchk.librarymanagement.event.mail;

import io.github.resilience4j.retry.annotation.Retry;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import sn.unchk.librarymanagement.config.EmailConfigProperties;
import sn.unchk.librarymanagement.event.EventType;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MailNotification {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final EmailConfigProperties emailConfigProperties;
    private static final Logger log = LoggerFactory.getLogger(MailNotification.class);

    @Async
    @Retry(name = "notificationEmailMember", fallbackMethod = "handleNotificationEmailFailure")
    public void sendEmailNotification(EventType eventType, String email, Map<String, Object> parameters) throws MessagingException {
        log.info("Try to send email to user {}", email);
        Context context = new Context();
        context.setVariables(parameters);
        String subject = eventType.retrieveSubject();
        context.setVariable("subject", subject);
        try {
            String bodyMail = templateEngine.process(eventType.retrieveTemplate(), context);
            MimeMessage mailMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);
            helper.setFrom(emailConfigProperties.getUsername());
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(bodyMail, true);
            mailSender.send(mailMessage);
            log.info("Mail sending successfully to user {}", email);
        }catch (Exception e){
            throw new RuntimeException("Error sending email to: " + email, e);
        }
    }

    public void handleNotificationEmailFailure(EventType eventType, String email, Map<String, Object> parameters, Exception e){
        log.error("Error when sending mail {} to user {} : {}", eventType, email, e.getMessage());
    }
}
