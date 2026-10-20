package org.example.model;

public class Book {

    private String title;
    private String author;
    private String isbn;
    private int publicationYear;
    private boolean available;

    public Book(String title, String author,
                String isbn, int publicationYear) {

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException(
                    "Book title cannot be empty.");
        }

        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException(
                    "Author name cannot be empty.");
        }

        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException(
                    "ISBN cannot be empty.");
        }

        if (publicationYear <= 0) {
            throw new IllegalArgumentException(
                    "Publication year must be valid.");
        }

        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.available = true;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }


}