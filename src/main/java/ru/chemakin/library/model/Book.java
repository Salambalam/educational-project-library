package ru.chemakin.library.model;

public class Book {
    private String name;
    private String author;
    private String yearOfPublishing;
    private int personId;

    public Book(String name, String author, String yearOfPublishing, int personId) {
        this.name = name;
        this.author = author;
        this.yearOfPublishing = yearOfPublishing;
        this.personId = personId;
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

    public String getYearOfPublishing() {
        return yearOfPublishing;
    }

    public void setYearOfPublishing(String yearOfPublishing) {
        this.yearOfPublishing = yearOfPublishing;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
