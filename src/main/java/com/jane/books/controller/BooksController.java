package com.jane.books.controller;

import com.jane.books.entity.Book;
import com.jane.books.exception.BookErrorResponse;
import com.jane.books.exception.BookNotFoundException;
import com.jane.books.request.BookRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Tag(name = "Books Rest API Endpoints", description = "Operations related to books.")
@RestController
@RequestMapping("/api/books")
public class BooksController {
    private List<Book> books = new ArrayList<>();

    public BooksController(){
        initializeBooks();
    }

    private void initializeBooks(){
        books.addAll(List.of(
                new Book( 1, "Computer Science Pro","Chad Darby","Computer Science", 5),
                new Book( 2, "Java Spring Master","Eric Roby","Computer Science", 5),
                new Book( 3, "Why 1+1 Rocks","Adil A.","Math", 5),
                new Book( 4, "How Bears Hibernate","Bob B.","Science", 2),
                new Book( 5, "A Pirate's Treasure","Curt C.","History", 3),
                new Book( 6, "Why 2+2 is Better","Dan D.","Math", 1)
        ));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Operation(summary = "Get all books", description = "Retrieve all books")
    public List<Book> getBooks(@Parameter(description = "Optional query parameter") @RequestParam(required = false) String category) {
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

    @Operation(summary = "Get a book by id", description = "Retrieve a book by passing its id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Book getBookById(@Parameter(description = "id of the book to retrieve")@PathVariable @Min(value = 1) long id){
//        for(Book book: books){
//            if(book.getId()== id){
//                return book;
//            }
//        }
//        return null;
        return (books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("Book not found - "+ id)));
    }

    @Operation(summary = "Create a book", description = "Store a new book by providing its details.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void addBook(@Valid @RequestBody BookRequest newBook){
        long id = books.isEmpty()? 1: books.get(books.size() - 1).getId() + 1;
        books.add(convertToBook(id, newBook));
    }

    @Operation(summary = "Update a book", description = "Change the content of a book by giving the content and the id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public Book updateBook(@Parameter(description = "id of thr book to update") @PathVariable @Min(value = 1) long id, @Valid @RequestBody BookRequest bookRequest){
        for(int i = 0; i < books.size(); i++){
            if(books.get(i).getId() == id){
                Book updatedBook = convertToBook(id, bookRequest);
                books.set(i, updatedBook);
                return updatedBook;
            }
        }
        throw new BookNotFoundException("Book not found - " + id);
    }

    @Operation(summary = "Delete a book", description = "Delete a book from the list by its id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBook(@Parameter(description = "id of the book to delete") @PathVariable @Min(value = 1) long id){
        books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("Book not found - "+ id));

        books.removeIf(book -> book.getId() == id);
    }
    private Book convertToBook(long id, BookRequest bookRequest){
        return new Book(id, bookRequest.getTitle(), bookRequest.getAuthor(), bookRequest.getCategory(), bookRequest.getRating());
    }

}
