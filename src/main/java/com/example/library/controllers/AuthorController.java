package com.example.library.controllers;

import java.util.*;

// import javax.validation.Valid;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.library.models.Author;
import com.example.library.models.BookResponse;
import com.example.library.services.AuthorService;
import com.example.library.services.BookService;


@RestController
@RequestMapping("api/v1/authors")
public class AuthorController {
    @Autowired
    AuthorService authorService;
    @Autowired
    BookService bookService;


    @GetMapping
    public ResponseEntity<List<Author>> allAuthors(){
        return new ResponseEntity<List<Author>>(authorService.allAuthors(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable String id){
        return new ResponseEntity<Author>(authorService.getAuthorById(id),HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Author> createAuthor( @Valid @RequestBody Author author){
        return new ResponseEntity<Author>(authorService.createAuthor(author),HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable String id, @RequestBody Author author){
        Author updatedAuthor = authorService.updateAuthor(id,author);
        if (updatedAuthor==null) {
            return new ResponseEntity<>("Author not found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Author>(updatedAuthor,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable String id){
        Boolean deletedAuthor = authorService.deleteAuthor(id);
        if (!deletedAuthor) {
            return new ResponseEntity<>("Author not found, can't delete",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Author deleted",HttpStatus.OK);
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<List<BookResponse>> allBooksByAuthor(@PathVariable String id){
        return new ResponseEntity<List<BookResponse>>(bookService.allBooksByAuthor(id),HttpStatus.OK);
    }
    
}
