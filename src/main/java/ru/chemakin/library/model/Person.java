package ru.chemakin.library.model;

import javax.validation.constraints.*;

public class Person {
    private int person_id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Pattern(regexp = "[А-Я][а-я]+ [А-Я][а-я]+ [А-Я][а-я]+",
            message = "You name should be in this format: Фамилия Имя Отчество")
    private String name;
    @Min(value = 1900, message = "Year of Birth should be grater 1900")
    @Max(value = 2023, message = "Year of Birth should be less then 2023")
    private int yearOfBirth;
    public Person(int id, String name, int yearOfBirth) {
        this.person_id = id;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public Person() {
    }
    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

}
