package ru.chemakin.library.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Book {
    @NotEmpty(message = "Book name should not be empty")
    @Size(min = 2, max = 50, message = "Book name should be between 2 and 50 characters")
    private String name;
    @NotEmpty(message = "Author Name should not be empty")
    @Pattern(regexp = "[А-Я][а-я]+ [А-Я][а-я]+",
            message = "Author name should be in this format: Имя Отчество")
    private String author;

    @Max(value = 2023, message = "Year of publishing should be less then 2023.")
    private int yearOfPublishing;
    private Integer personId;
    private int bookId;

    public Book(String name, String author, int yearOfPublishing, int bookId) {
        this.name = name;
        this.author = author;
        this.yearOfPublishing = yearOfPublishing;
        this.bookId = bookId;
    }

    public Book() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfPublishing() {
        return yearOfPublishing;
    }

    public void setYearOfPublishing(int yearOfPublishing) {
        this.yearOfPublishing = yearOfPublishing;
    }

    public Integer getPersonId() {
        return personId;
    }
    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
