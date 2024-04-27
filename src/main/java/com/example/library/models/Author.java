package com.example.library.models;

// import javax.validation.constraints.NotBlank;
// import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Document(collection = "authors")
@Data
@AllArgsConstructor
public class Author {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    
    @NotNull
    @NotBlank(message = "Name is required")
    private String name;
    private String biography;
}
