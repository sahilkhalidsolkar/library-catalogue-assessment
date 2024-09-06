package com.example.libraryBookCatalogue.Dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class BookDto {

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
