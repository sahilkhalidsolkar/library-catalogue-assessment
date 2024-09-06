package com.example.libraryBookCatalogue.service;

import com.example.libraryBookCatalogue.Dto.BookDto;
import com.example.libraryBookCatalogue.exception.BookNotFoundException;
import com.example.libraryBookCatalogue.mapper.BookMapper;
import com.example.libraryBookCatalogue.models.Book;
import com.example.libraryBookCatalogue.repository.BookRepository;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final EmailService emailService;

    public Page<BookDto> getAllBooks(int pageNo, int pageSize, String sortBy) {
        Pageable pageRequest;
        if (Objects.isNull(sortBy) || sortBy.isEmpty()) {
            pageRequest = PageRequest.of(pageNo, pageSize);
        } else {
            Sort customSort = Sort.by(Sort.Direction.ASC, sortBy);
            pageRequest = PageRequest.of(pageNo, pageSize, customSort);
        }

        Page<Book> books = bookRepository.findAll(pageRequest);
        List<BookDto> booklist = books.stream().map(bookMapper::mapToBookDto).collect(Collectors.toList());
        return new PageImpl<BookDto>(booklist, pageRequest, books.getTotalElements());

    }

    public Page<BookDto> getFilteredBooks(int pageNo, int pageSize, String filterBy, String filterByValue) {
        Page<Book> books;
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        switch (filterBy.toLowerCase().trim()) {
            case "genre": {
                books = bookRepository.findByGenre(filterByValue, pageRequest);
                break;
            }
            case "author": {
                books = bookRepository.findByAuthor(filterByValue, pageRequest);
                break;

            }
            case "publication_date": {
                books = bookRepository.findByPublicationDate(filterByValue, pageRequest);
                break;
            }
            default: {
                books = bookRepository.findAll(pageRequest);
            }
        }
        List<BookDto> booklist = books.stream().map(bookMapper::mapToBookDto).collect(Collectors.toList());
        return new PageImpl<BookDto>(booklist, pageRequest, books.getTotalElements());

    }

    public BookDto getSingleBook(int bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("the Book does not exist"));
        return bookMapper.mapToBookDto(book);
    }

    public BookDto addBook(BookDto bookDto) {
        Book book = bookMapper.mapToBookEntity(bookDto);
        Book savedBook = bookRepository.save(book);

        emailService.sendThymeLeafEmail(
                "sahilksolkar20@gmail.com",
                "New Book added" + book.getTitle(),
                book.getTitle()+ "is added to the library",
                book.getAuthor(),
                "admin"
                );

        return bookMapper.mapToBookDto(savedBook);
    }


    public BookDto updateBook(BookDto bookDto, int bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("the Book does not exist"));
        book.setAuthor(bookDto.getAuthor());
        book.setTitle(bookDto.getTitle());
        book.setGenre(bookDto.getGenre());
        book.setPrice(bookDto.getPrice());
        book.setPublication(bookDto.getPublication());
        book.setPublicationDate(bookDto.getPublicationDate());

        Book savedBook = bookRepository.save(book);
        return bookMapper.mapToBookDto(savedBook);
    }

    public String deleteBook(int id) {

        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("the book does not exists"));
        bookRepository.delete(book);
        emailService.sendThymeLeafEmail(
                "sahilksolkar20@gmail.com",
                " Book Deleted" + book.getTitle(),
                book.getTitle()+ "is deleted from the library",
                book.getAuthor(),
                "admin"
        );
        return "Book deleted successfully";

    }


}
