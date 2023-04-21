package ru.chemakin.library.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.chemakin.library.model.Book;

import java.util.List;

public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void index(){
        jdbcTemplate.query("SELECT * FROM Book",
                new BeanPropertyRowMapper<>(Book.class));
    }

    public void show(int BookId){
        jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?",
                new Object[]{BookId}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
        // убрать orElse после добаления валидации
    }


    public void save(Book book){
        jdbcTemplate.update("INSERT INTO book(NAME, AUTHOR, YEAR_OF_PUBLISHING) VALUES(?, ?, ?)",
                book.getName(), book.getAuthor(), book.getYearOfPublishing());
    }

    public void update(int bookId, Book updatedBook){
        jdbcTemplate.update("UPDATE book SET name=?, author=?, year_of_publishing=? WHERE book_id=?",
                updatedBook.getName(), updatedBook.getAuthor(),
                updatedBook.getYearOfPublishing(), bookId);
    }
    // написать методы для personID присовение книги

    public void delete(int bookId){
        jdbcTemplate.update("DELETE FROM book WHERE book_id=?", bookId);
    }
}
