package ru.chemakin.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.chemakin.library.model.Book;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM Book",
                new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Book> show(String name){
        return jdbcTemplate.query("SELECT * FROM Book WHERE name=?", new Object[]{name},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    public Book show(int BookId){
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?",
                new Object[]{BookId}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
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
