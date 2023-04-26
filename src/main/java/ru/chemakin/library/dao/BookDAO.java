package ru.chemakin.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.chemakin.library.model.Book;
import ru.chemakin.library.model.Person;

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

    public List<Person> indexPerson(){
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
        // query -  метод используется для выполнения запроса к базе данных и извлечения данных из нее.
        // BeanPropertyRowMapper - реализует RowMapper, который используется для преобразования строк БД в Java объекты

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

    public boolean ownershipCheck(int bookId){
        Integer count = jdbcTemplate.queryForObject("SELECT person_id FROM book WHERE book_id=?",
                new Object[]{bookId}, Integer.class);
        return count != null;
    }

    public Person showPerson(int bookId){
        if(!ownershipCheck(bookId)){
            return null;
        }
        return jdbcTemplate.queryForObject("SELECT p.person_id, p.name, p.year_of_birth " +
                        "FROM Person p JOIN Book b ON p.person_id = b.person_id " +
                        "WHERE b.book_id=?", new Object[]{bookId},
                new BeanPropertyRowMapper<>(Person.class));
    }

    public void assignOwner(int personId, int bookId){
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE book_id=?;",
                personId, bookId);
    }

    public Person showOnePerson(int id){
        return jdbcTemplate.queryForObject("SELECT * FROM Person WHERE person_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class));
    }

    public void release(int BookId){
        jdbcTemplate.update("UPDATE book SET person_id=NULL WHERE book_id=?", BookId);
    }

}
