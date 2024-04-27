package com.example.library.services;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.library.models.Author;
import com.example.library.models.Book;
import com.example.library.models.BookResponse;
import com.example.library.repositories.AuthorRepository;
import com.example.library.repositories.BookRepository;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;

    public List<BookResponse> allBooks(){
        List<BookResponse> bookResponses=bookRepository.findAll().stream()
        .map(this::mapToBookResponse)
        .collect(Collectors.toList());
        return bookResponses;
    }

    public List<BookResponse> allBooksByAuthor(String id){
        List<BookResponse> bookResponses=bookRepository.findByAuthor(id).stream()
        .map(this::mapToBookResponse)
        .collect(Collectors.toList());
        System.out.println(bookResponses);
        return bookResponses;
    }

    public ResponseEntity<BookResponse> getBookByID(String id){
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            return null;
        }

        return ResponseEntity.ok(mapToBookResponse(book.get()));
    }

    public Book createBook(Book book){
        book.setCreatedAt(new Date());
        book.setUpdatedAt(new Date());
        book.setRented(false);
        return bookRepository.save(book);
    }
    
    public Book updateBook(String id,Book book){
        Optional<Book> existedBook= bookRepository.findById(id);
        if (existedBook.isPresent()) {
            if (book.getTitle() !=null){
                existedBook.get().setTitle(book.getTitle());
            }
            if (book.getIsbn() !=null){
                existedBook.get().setIsbn(book.getIsbn());
            }
            if (book.getPublicationYear() !=null){
                existedBook.get().setPublicationYear(book.getPublicationYear());
            }
        } else {
            return null;
        }

        existedBook.get().setUpdatedAt(new Date());
        return bookRepository.save(existedBook.get());
    }

    public Boolean deleteBook(String id){
        Optional<Book> existedBook= bookRepository.findById(id);
        if (existedBook.isEmpty()) return false;

        try {
            bookRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private BookResponse mapToBookResponse(Book book) {
        
        Optional<Author> author = authorRepository.findById(book.getAuthor());
        if (author.isEmpty()) {
            author= null;
        }
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getPublicationYear(),
                author.get(),
                book.getCreatedAt(),
                book.getUpdatedAt(),
                book.isRented()
        );
    }

    public List<BookResponse> allRentedBooks() {
        List<BookResponse> bookResponses=bookRepository.findByRented(true).stream()
        .map(this::mapToBookResponse)
        .collect(Collectors.toList());
        return bookResponses;
        
    }

    public List<BookResponse> allAvailableBooks() {
        List<BookResponse> bookResponses=bookRepository.findByRented(false).stream()
        .map(this::mapToBookResponse)
        .collect(Collectors.toList());
        return bookResponses;
        
    }
}
