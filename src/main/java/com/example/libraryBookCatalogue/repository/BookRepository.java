package com.example.libraryBookCatalogue.repository;

import com.example.libraryBookCatalogue.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    Page<Book> findByGenre(String genre, Pageable page);
    Page<Book> findByAuthor(String author, Pageable page);
    Page<Book> findByPublicationDate(String publicationDate, Pageable page);

}
