package org.example;

import org.example.factory.BookFactory;
import org.example.model.Book;
import org.example.model.BorrowingRecord;
import org.example.model.Patron;
import org.example.service.LibraryService;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static void main(String[] args) {

        LibraryService library = new LibraryService();

        Book book = BookFactory.createBook(
                "Clean Code",
                "Robert C. Martin",
                "ISBN-101",
                2008
        );

        Patron patron = new Patron(
                "P-101",
                "Aquib",
                "aquib@example.com"
        );

        library.addBook(book);
        library.registerPatron(patron);
        try {
            library.checkoutBook("ISBN-101", "P-101");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        BorrowingRecord record =
                library.checkoutBook("ISBN-101", "P-101");
        try {
            library.checkoutBook("ISBN-101", "P-101");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }


        System.out.println("Available: " + book.isAvailable());
        // false

        library.returnBook(record.getRecordId());

        System.out.println("Available: " + book.isAvailable());
        // true

        System.out.println(
                "History size: "
                        + library.getBorrowingHistory("P-101").size()
        );
        // 1

        System.out.println(
                library.searchBooksByTitle("clean").size()
        );

        library.updateBook(
                "ISBN-101",
                "Clean Code Revised",
                "Robert C. Martin",
                2008
        );

        System.out.println(
                library.searchBookByIsbn("ISBN-101").getTitle()
        );


        library.updatePatron(
                "P-101",
                "Aquib Shaikh",
                "aquib.shaikh@example.com"
        );

        System.out.println(
                library.getBorrowingHistory("P-101")
                        .get(0)
                        .getPatron()
                        .getName()
        );

//        library.removeBook("ISBN-101");

        System.out.println(
                library.searchBooksByTitle("Clean Code").size()
        );

        BorrowingRecord bRecord =
                library.checkoutBook("ISBN-101", "P-101");

        try {
            library.removeBook("ISBN-101");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        library.returnBook(bRecord.getRecordId());
    }
}