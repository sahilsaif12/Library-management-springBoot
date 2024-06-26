package com.example.library.repositories;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.library.models.Author;

@Repository
public interface AuthorRepository extends MongoRepository<Author,String> {
    
}
