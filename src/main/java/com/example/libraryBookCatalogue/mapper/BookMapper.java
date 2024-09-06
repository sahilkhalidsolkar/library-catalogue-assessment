package com.example.libraryBookCatalogue.mapper;


import com.example.libraryBookCatalogue.Dto.BookDto;
import com.example.libraryBookCatalogue.models.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto mapToBookDto(Book book);
    Book mapToBookEntity(BookDto bookDto);
}
