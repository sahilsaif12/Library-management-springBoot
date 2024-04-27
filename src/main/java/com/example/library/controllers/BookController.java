package com.example.library.controllers;

import java.util.List;

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

import com.example.library.models.Book;
import com.example.library.models.BookResponse;
import com.example.library.services.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/books")
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookResponse>> allBooks(){
        return new ResponseEntity<List<BookResponse>>(bookService.allBooks(),HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookByID(@PathVariable String id){
        ResponseEntity<BookResponse> bookResponse =bookService.getBookByID(id);
        return bookResponse;
    }
    
    @GetMapping("/rented")
    public ResponseEntity<List<BookResponse>> allRentedBooks(){
        return new ResponseEntity<List<BookResponse>>(bookService.allRentedBooks(),HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<List<BookResponse>> allAvailableBooks(){
        return new ResponseEntity<List<BookResponse>>(bookService.allAvailableBooks(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> createBook( @Valid @RequestBody Book book){
        return new ResponseEntity<Book>(bookService.createBook(book),HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable String id, @RequestBody Book book){
        Book updatedBook = bookService.updateBook(id,book);
        if (updatedBook==null) {
            return new ResponseEntity<>("Book not found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Book>(updatedBook,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable String id){
        Boolean deletedBook = bookService.deleteBook(id);
        if (!deletedBook) {
            return new ResponseEntity<>("Book not found, can't delete",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Book deleted",HttpStatus.OK);
    }
}
