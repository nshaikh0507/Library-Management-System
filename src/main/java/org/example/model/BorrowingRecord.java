
package org.example.model;

import java.time.LocalDate;
import org.example.model.*;

public class BorrowingRecord {

    private final String recordId;
    private final Book book;
    private final Patron patron;
    private final LocalDate borrowDate;

    private LocalDate returnDate;

    public BorrowingRecord(
            String recordId,
            Book book,
            Patron patron,
            LocalDate borrowDate) {

        this.recordId = recordId;
        this.book = book;
        this.patron = patron;
        this.borrowDate = borrowDate;
    }

    public String getRecordId() {
        return recordId;
    }

    public Book getBook() {
        return book;
    }

    public Patron getPatron() {
        return patron;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isReturned() {
        return returnDate != null;
    }

    public void markReturned(LocalDate returnDate) {
        if (isReturned()) {
            throw new IllegalStateException(
                    "This book has already been returned.");
        }

        if (returnDate.isBefore(borrowDate)) {
            throw new IllegalArgumentException(
                    "Return date cannot be before borrow date.");
        }

        this.returnDate = returnDate;
    }
}
