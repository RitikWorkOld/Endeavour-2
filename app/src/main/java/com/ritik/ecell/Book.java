package com.ritik.ecell;

public class Book {

    private String subject;
    private String email;
    private String message;

    public Book() {
    }

    public Book(String subject, String email, String message) {
        this.subject = subject;
        this.email = email;
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
