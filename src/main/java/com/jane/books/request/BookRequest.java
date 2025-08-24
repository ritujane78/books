package com.jane.books.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class BookRequest {
    @Size(min = 1, max = 30, message = "Title must have at least 1 and at most 30 characters.")
    private String title;

    @Size(min = 1, max = 40, message = "Author must have at least 1 and at most 40 characters.")
    private String author;

    @Size(min = 1, max = 30, message = "Category must have at least 1 and at most 30 characters.")
    private String category;

    @Min(value = 1, message = "Value must be at least 1")
    @Max(value = 5, message = "Value cannot be more than 5")
    private int rating;

    public BookRequest(String title, String author, String category, int rating) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
