package com.example.library.models;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
// import jakarta.persistence.*/;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Document(collection = "books")
@Data
@AllArgsConstructor
public class Book {
    private static final String ISBN_PATTERN = "^(?:ISBN(?:-10)?:?\\s*)?" +
    "(?=[0-9X]{10}$|(?=(?:[0-9]+[-\\ ]){3})" +
    "[-\\ 0-9X]{13}$|97[89][0-9]{10}$|" +
    "(?=(?:[0-9]+[-\\ ]){4})[-\\ 0-9]{17}$)" +
    "[-\\ 0-9]{1,5}[-\\ 0-9X]+$";


    @Id
    private String id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author Id is required")
    private String author;

    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = ISBN_PATTERN, message = "Invalid ISBN format")
    private String isbn;

    @NotBlank(message = "Publication Year is required")
    private String publicationYear;

    private boolean rented;

    @CreatedDate
    private Date createdAt;
  
    @LastModifiedDate
    private Date updatedAt;
}

