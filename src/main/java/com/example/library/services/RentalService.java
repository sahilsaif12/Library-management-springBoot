package com.example.library.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.library.models.Book;
import com.example.library.models.Rental;
import com.example.library.repositories.BookRepository;
import com.example.library.repositories.RentalRepository;

@Service
public class RentalService {
    @Autowired
    RentalRepository rentalRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    public List<Rental> allRentals(){
        return rentalRepository.findAll();
    }

    public ResponseEntity<?> createRental(Rental rental) {
        Optional<Book> book = bookRepository.findById(rental.getBookId());
        if (book.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("book not found with the bookId");
        }
        if (book.get().isRented()) {
            return ResponseEntity.status(HttpStatus.OK).body("This book is already rented");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(rental.getRentalDate(), formatter);
        LocalDate newDate = date.plusDays(14);
        String returnDate = newDate.format(formatter);
        rental.setReturnDate(returnDate);
        mongoTemplate.update(Book.class)
        .matching(Criteria.where("_id").is(rental.getBookId()))
        .apply(new Update().set("rented", true))
        .first();
        
        rental.setReturned(false);
        rentalRepository.save(rental);
        return ResponseEntity.status(HttpStatus.OK).body(rental);
    }

    public Rental returnRental(String id) {
        Optional<Rental> existedRental= rentalRepository.findById(id);
        if (existedRental.isEmpty()) {
            return null;
        }
        existedRental.get().setReturned(true);
        rentalRepository.save(existedRental.get());
        mongoTemplate.update(Book.class)
        .matching(Criteria.where("_id").is(existedRental.get().getBookId()))
        .apply(new Update().set("rented", false))
        .first();
        
        return existedRental.get();
    }

    public List<Rental> allOverdueBooks() {
        List<Rental> overdueRentals=rentalRepository.findByReturned(false).stream()
        .filter(this::overdueRental)
        .collect(Collectors.toList());
        return overdueRentals;
    }
    
    private Boolean overdueRental(Rental rental) {
        
        LocalDate currentDate = LocalDate.now();
        LocalDate returnDate = LocalDate.parse(rental.getReturnDate(),DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return currentDate.isAfter(returnDate);
    }

}
