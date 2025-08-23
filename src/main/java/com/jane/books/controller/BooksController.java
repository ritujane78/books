package com.jane.books.controller;

import com.jane.books.entity.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BooksController {
    private List<Book> books = new ArrayList<>();

    public BooksController(){
        initializeBooks();
    }

    private void initializeBooks(){
        books.addAll(List.of(
                new Book("Title one","Author one","science"),
                new Book("Title two","Author two","science"),
                new Book("Title three","Author three","science"),
                new Book("Title four","Author four","history"),
                new Book("Title five","Author five","science"),
                new Book("Title six","Author six","science"),
                new Book("Title seven","Author seven","science"),
                new Book("Title eight","Author eight","mathematics")
        ));
    }

    @GetMapping("/api/books")
    public List<Book> getBooks(){
        return books;
    }

    @GetMapping("/api/books/{title}")
    public Book getBookByTitle(@PathVariable String title){
//        for(Book book: books){
//            if(book.getTitle().equalsIgnoreCase(title)){
//                return book;
//            }
//        }
//        return null;
        return (books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null));
    }
}
