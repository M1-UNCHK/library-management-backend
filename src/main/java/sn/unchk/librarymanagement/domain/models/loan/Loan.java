package sn.unchk.librarymanagement.domain.models.loan;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import sn.unchk.librarymanagement.domain.exceptions.MalformedFieldException;
import sn.unchk.librarymanagement.domain.models.BaseModel;
import sn.unchk.librarymanagement.domain.models.book.Book;
import sn.unchk.librarymanagement.domain.models.member.Reader;
import sn.unchk.librarymanagement.domain.validation.Create;
import sn.unchk.librarymanagement.domain.validation.Update;

import java.time.LocalDate;

import static sn.unchk.librarymanagement.constant.GlobalConstant.REQUIRED_FIELD_NAME;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Loan extends BaseModel {
    @ManyToOne(optional = false)
    @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class })
    private Book book;

    @ManyToOne(optional = false)
    @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class })
    private Reader reader;

    @Column(nullable = false)
    @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class })
    private LocalDate loanDate;

    @Column(nullable = false)
    @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class })
    private LocalDate dueDate;

    private LocalDate returnedDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = REQUIRED_FIELD_NAME, groups = { Create.class, Update.class })
    private LoanStatus status;

    private boolean hasNotifyForRemind;

    private boolean hasNotifyForDelay;

    private static final int DAY_MATURITY = 20;
    public static final int REMIND_DAY = 3;

    public static Loan addNewLoan(Book book, Reader reader, LocalDate date) {
        validateField(book.getId(), "book");
        validateField(reader.getId(), "reader");

        if(date.isAfter(LocalDate.now()))
            throw new MalformedFieldException("date", "Loan Date cannot be after today");

        if(!book.hasAvailableStock())
            throw new MalformedFieldException("stock", String.format("Book %s has not available stock", book.getName()));

        return Loan.builder()
                .book(book)
                .reader(reader)
                .loanDate(date)
                .dueDate(date.plusDays(DAY_MATURITY))
                .status(LoanStatus.IN_PROGRESS)
                .build();
    }

    public void returnLoan(LocalDate date) {
        if(date.isAfter(LocalDate.now()))
            throw new MalformedFieldException("date", "Returned Date cannot be after today");

        this.returnedDate = date;
        this.status = LoanStatus.RETURNED;
    }
}
