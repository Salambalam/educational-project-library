package ru.chemakin.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.chemakin.library.model.Book;
import ru.chemakin.library.model.Person;

import java.util.List;
import java.util.Optional;

/**
 * Класс BookDAO предоставляет доступ к базе данных и обеспечивает взаимодействие между
 * BookController и базой данных для управления книгами и их владельцами.
 */
@Component
public class BookDAO {

    

    public BookDAO() {
        
    }

    public Object index() {
        return null;
    }

    public Object show(int id) {
        return null;
    }

    public Object ownershipCheck(int id) {
        return null;
    }

    public Object showPerson(int id) {
        return null;
    }

    public Object indexPerson() {
        return null;
    }

    public void assignOwner(int personId, Integer id) {
    }

    public void delete(int id) {
    }

    public void release(int id) {
    }

    public void update(int id, Book book) {
    }

    public void save(Book book) {
    }
}
