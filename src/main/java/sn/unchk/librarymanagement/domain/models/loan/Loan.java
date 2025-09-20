package sn.unchk.librarymanagement.domain.models.loan;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import sn.unchk.librarymanagement.domain.exceptions.MalformedFieldException;
import sn.unchk.librarymanagement.domain.models.BaseModel;
import sn.unchk.librarymanagement.domain.models.book.Book;
import sn.unchk.librarymanagement.domain.models.member.Reader;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Loan extends BaseModel {
    @ManyToOne(optional = false)
    private Book book;

    @ManyToOne(optional = false)
    private Reader reader;

    @Column(nullable = false)
    private LocalDate loanDate;

    private LocalDate returnedDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoanStatus status;

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
