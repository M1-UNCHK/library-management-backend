package sn.unchk.librarymanagement.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import sn.unchk.librarymanagement.domain.models.loan.Loan;

@Getter
public class LoanEvent extends ApplicationEvent {
    private String email;
    private String lastname;
    private Loan loan;
    private final EventType eventType;

    public LoanEvent(Object source, String email, String lastname, Loan loan, EventType eventType) {
        super(source);
        this.email = email;
        this.lastname = lastname;
        this.loan = loan;
        this.eventType = eventType;
    }
}
