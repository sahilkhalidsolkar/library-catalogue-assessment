package com.example.libraryBookCatalogue.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @NotNull
    private String title;
    @NotNull
    private String genre;
    @NotNull
    private Integer price;
    @NotNull
    private String author;
    @NotNull
    private String publication;
    @NotNull
    private String publicationDate;

}
