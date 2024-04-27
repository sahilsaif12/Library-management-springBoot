package com.example.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.library.models.Author;
import com.example.library.repositories.AuthorRepository;

// import jakarta.validation.Valid;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    
    public List<Author> allAuthors(){
        return authorRepository.findAll();
    }
    
    public Author getAuthorById(String id){
        return authorRepository.findById(id).get();
    }

    public Author createAuthor(Author author){
        return authorRepository.save(author);
    }

    public Author updateAuthor(String id,Author author){
        Optional<Author> existedAuthor = authorRepository.findById(id);
        if (existedAuthor.isPresent()) {
            if (author.getName()!=null){
                existedAuthor.get().setName(author.getName());
            }
            if (author.getBiography()!=null){
                existedAuthor.get().setBiography(author.getBiography());
            }
        } else {
            return null;
        }
        return authorRepository.save(existedAuthor.get());
    }

    public Boolean deleteAuthor(String id){
        Optional<Author> existedAuthor = authorRepository.findById(id);
        if (existedAuthor.isEmpty()) return false;

        try {
            authorRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

