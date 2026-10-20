
package org.example.model;


public class Patron {

    private String patronId;
    private String name;
    private String email;

    public Patron(String patronId, String name, String email) {

        if (patronId == null || patronId.isBlank()) {
            throw new IllegalArgumentException(
                    "Patron ID cannot be empty.");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Patron name cannot be empty.");
        }

        if (email == null || email.isBlank()
                || !email.contains("@")) {
            throw new IllegalArgumentException(
                    "Please provide a valid email.");
        }

        this.patronId = patronId;
        this.name = name;
        this.email = email;
    }

    public String getPatronId() {
        return patronId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
