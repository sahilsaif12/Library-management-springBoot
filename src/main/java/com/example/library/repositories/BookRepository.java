package com.example.library.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.library.models.Book;

@Repository
public interface BookRepository  extends MongoRepository<Book,String> {
    List<Book> findByAuthor(String author);
    List<Book> findByRented(Boolean rented);
}
