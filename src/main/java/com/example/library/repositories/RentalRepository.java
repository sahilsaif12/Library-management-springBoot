package com.example.library.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.library.models.Rental;

@Repository
public interface RentalRepository extends MongoRepository<Rental,String> {

    List<Rental> findByReturnDateBefore(String date);

    Collection<Rental> findByReturned(boolean returned);
    
}
