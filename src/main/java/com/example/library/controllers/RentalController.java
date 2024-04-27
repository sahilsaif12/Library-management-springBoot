package com.example.library.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.library.models.Rental;
import com.example.library.services.RentalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/rentals")
public class RentalController {
    @Autowired
    RentalService rentalService;

    @GetMapping
    public ResponseEntity<List<Rental>> allRentals(){
        return new ResponseEntity<List<Rental>>(rentalService.allRentals(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createRental(@Valid @RequestBody Rental rental){
        ResponseEntity<?> createdRental=rentalService.createRental(rental);
        if (createdRental==null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book Not found");
        }
        return createdRental;
    }

    @PatchMapping("/return/{id}")
    public ResponseEntity<?> returnRental(@PathVariable String id){
        Rental returnedRental = rentalService.returnRental(id);
        if (returnedRental==null) {
            return new ResponseEntity<>("Rental not found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Rental>(returnedRental,HttpStatus.OK);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Rental>> allOverdueBooks(){
        return new ResponseEntity<List<Rental>>(rentalService.allOverdueBooks(),HttpStatus.OK);
    }
}
