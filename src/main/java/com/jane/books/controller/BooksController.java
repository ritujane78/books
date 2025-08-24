package com.jane.books.controller;

import com.jane.books.entity.Book;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/books")
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

    @GetMapping
    public List<Book> getBooks(@RequestParam(required = false) String category) {
        if(category == null){
            return books;
        }
//        List<Book> filteredBooks = new ArrayList<>();
//        for (Book book: books){
//            if(book.getCategory().equalsIgnoreCase(category)){
//                filteredBooks.add(book);
//            }
//        }
//        return filteredBooks;

        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();

    }

    @GetMapping("/{title}")
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

    @PostMapping
    public void addBook(@RequestBody Book newBook){
        boolean isNewBook = books.stream()
                .noneMatch(book -> book.getTitle().equalsIgnoreCase(newBook.getTitle()));

        if(isNewBook){
            books.add(newBook);
        }
//        for(Book book: books){
//            if(book.getTitle().equalsIgnoreCase(newBook.getTitle())){
//                return;
//            }
//        }
//        books.add(newBook);
    }
    @PutMapping("/{title}")
    public void updateBook(@PathVariable String title, @RequestBody Book updatedBook){
        for(int i = 0; i < books.size(); i++){
            if(books.get(i).getTitle().equalsIgnoreCase(title)){
                books.set(i, updatedBook);
                return;
            }
        }
    }
    @DeleteMapping("/{title}")
    public void deleteBook(@PathVariable String title){
        books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }
}
