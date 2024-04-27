package com.example.library.models;

import java.util.Date;

import lombok.Data;

@Data
public class BookResponse {
    private String id;
    private String title;
    private String isbn;
    private String publicationYear;
    private Author author;
    private Date createdAt;
    private Date updatedAt;
    private boolean rented;
    public BookResponse(String id, String title, String isbn, String publicationYear, Author author,Date createdAt, Date updatedAt,boolean rented) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.rented=rented;
    }
}
