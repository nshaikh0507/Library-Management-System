
package org.example.service;

import org.example.model.Book;
import org.example.model.Patron;
import org.example.model.BorrowingRecord;
import org.example.observer.LibraryObserver;
import org.example.observer.LoggingLibraryObserver;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class LibraryService {

    private static final Logger LOGGER =
            Logger.getLogger(LibraryService.class.getName());

    private final List<Book> books = new ArrayList<>();
    private final List<Patron> patrons = new ArrayList<>();
    private final List<BorrowingRecord> records = new ArrayList<>();

    public void addBook(Book book) {
        boolean exists = books.stream()
                .anyMatch(b -> b.getIsbn().equals(book.getIsbn()));

        if (exists) {
            throw new IllegalArgumentException(
                    "Book with this ISBN already exists.");
        }

        books.add(book);
        LOGGER.info("Book added: " + book.getTitle());
    }

    public void registerPatron(Patron patron) {
        boolean exists = patrons.stream()
                .anyMatch(p ->
                        p.getPatronId().equals(patron.getPatronId()));

        if (exists) {
            throw new IllegalArgumentException(
                    "Patron ID already exists.");
        }

        patrons.add(patron);
        LOGGER.info("Patron registered: " + patron.getPatronId());
    }

    public BorrowingRecord checkoutBook(
            String isbn, String patronId) {

        Book book = books.stream()
                .filter(b -> b.getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Book not found."));

        Patron patron = patrons.stream()
                .filter(p -> p.getPatronId().equals(patronId))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Patron not found."));

        if (!book.isAvailable()) {
            throw new IllegalStateException(
                    "Book is currently unavailable.");
        }

        BorrowingRecord record = new BorrowingRecord(
                UUID.randomUUID().toString(),
                book,
                patron,
                LocalDate.now()
        );

        book.setAvailable(false);
        records.add(record);

        notifyObservers("Book checked out: " + isbn);
        LOGGER.info("Book checked out: " + isbn
                + " by patron " + patronId);

        return record;
    }

    public void returnBook(String recordId) {
        BorrowingRecord record = records.stream()
                .filter(r -> r.getRecordId().equals(recordId))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Borrowing record not found."));

        if (record.isReturned()) {
            throw new IllegalStateException(
                    "Book has already been returned.");
        }

        record.markReturned(LocalDate.now());
        record.getBook().setAvailable(true);

        notifyObservers(
                "Book returned: " + record.getBook().getIsbn()
        );

        LOGGER.info("Book returned: "
                + record.getBook().getIsbn());
    }

    public List<BorrowingRecord> getBorrowingHistory(
            String patronId) {

        return records.stream()
                .filter(r ->
                        r.getPatron().getPatronId().equals(patronId))
                .toList();
    }

    public List<Book> searchBooksByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle()
                        .toLowerCase()
                        .contains(title.toLowerCase()))
                .toList();
    }

    public List<Book> searchBooksByAuthor(String author) {
        return books.stream()
                .filter(book -> book.getAuthor()
                        .toLowerCase()
                        .contains(author.toLowerCase()))
                .toList();
    }

    public Book searchBookByIsbn(String isbn) {
        return books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Book not found."));
    }

    public void updateBook(
            String isbn,
            String title,
            String author,
            int publicationYear) {

        Book book = searchBookByIsbn(isbn);

        book.setTitle(title);
        book.setAuthor(author);
        book.setPublicationYear(publicationYear);

        LOGGER.info("Book updated: " + isbn);
    }

    public void removeBook(String isbn) {
        Book book = searchBookByIsbn(isbn);

        boolean currentlyBorrowed = records.stream()
                .anyMatch(record ->
                        record.getBook().getIsbn().equals(isbn)
                                && !record.isReturned());

        if (currentlyBorrowed) {
            throw new IllegalStateException(
                    "Cannot remove a book that is currently borrowed.");
        }

        books.remove(book);
        LOGGER.info("Book removed: " + isbn);
    }

    public void updatePatron(
            String patronId,
            String newName,
            String newEmail) {

        Patron patron = patrons.stream()
                .filter(p -> p.getPatronId().equals(patronId))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Patron not found."));

        patron.setName(newName);
        patron.setEmail(newEmail);

        LOGGER.info("Patron updated: " + patronId);
    }
    private final List<LibraryObserver> observers =
            new ArrayList<>();

    public LibraryService() {
        observers.add(new LoggingLibraryObserver());
    }

    public void addObserver(LibraryObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers(String message) {
        for (LibraryObserver observer : observers) {
            observer.onLibraryEvent(message);
        }
    }

}
