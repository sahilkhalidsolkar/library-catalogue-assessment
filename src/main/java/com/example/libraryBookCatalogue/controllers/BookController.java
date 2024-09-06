package com.example.libraryBookCatalogue.controllers;

import com.example.libraryBookCatalogue.Dto.BookDto;
import com.example.libraryBookCatalogue.models.Book;
import com.example.libraryBookCatalogue.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/library")
public class BookController {
    private final BookService bookService;

    @GetMapping("/book/all")
    public ResponseEntity<Page<BookDto>> getAllBooks(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "", required = false) String sortBy
    ) {
       return new ResponseEntity<>(bookService.getAllBooks(pageNo,pageSize,sortBy), HttpStatus.OK);
    }

    @GetMapping("/book/filter")
    public Page<BookDto> getFilteredBooks(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "filterBy", defaultValue = "", required = false) String filterBy,
            @RequestParam(value = "filterByValue", defaultValue = "", required = false) String filterByValue


    ){
        return bookService.getFilteredBooks(pageNo,pageSize,filterBy,filterByValue);
    }

    @PostMapping("/book")
    public ResponseEntity<BookDto>  addBook(@Valid @RequestBody BookDto bookDto){
        BookDto bookDto1 = bookService.addBook(bookDto);
        return new ResponseEntity<>(bookDto1,HttpStatus.CREATED);


    }
    @PutMapping("/book/{id}")
    public ResponseEntity<BookDto>  updateBook(@Valid @RequestBody BookDto bookDto,@PathVariable("id") Integer id ){
        BookDto bookDto1 = bookService.updateBook(bookDto, id);
        return  new ResponseEntity<>(bookDto1,HttpStatus.OK);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<String>  deleteBook( @PathVariable("id") Integer id){
        String msg = bookService.deleteBook(id);
        return new ResponseEntity<>(msg,HttpStatus.OK);
    }

}
