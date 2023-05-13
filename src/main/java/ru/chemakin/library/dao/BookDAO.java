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

    private final JdbcTemplate jdbcTemplate; // Поле, хранящее ссылку на объект JdbcTemplate, для выполнения SQL-запросов

    /**
     * Конструктор BookDAO внедряет JdbcTemplate для выполнения запросов к базе данных.
     * @param jdbcTemplate - шаблон для работы с БД.
     */
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Метод возвращает список всех книг из базы данных.
     * @return список книг.
     */
    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM Book",
                new BeanPropertyRowMapper<>(Book.class));
    }

    /**
     * Метод возвращает список всех людей из базы данных.
     * @return список людей.
     */
    public List<Person> indexPerson(){
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    /**
     * Метод проверяет наличие книги в базе данных по её имени.
     * @param name - имя книги.
     * @return объект Optional<Book> с книгой, если она найдена, или null, если её нет.
     */
    public Optional<Book> show(String name){
        return jdbcTemplate.query("SELECT * FROM Book WHERE name=?", new Object[]{name},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    /**
     * Метод возвращает книгу из базы данных по её ID.
     * @param BookId - ID книги.
     * @return объект Book или null, если книга не найдена.
     */
    public Book show(int BookId){
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?",
                new Object[]{BookId}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    /**
     * Метод добавляет книгу в базу данных.
     * @param book - книга для добавления.
     */
    public void save(Book book){
        jdbcTemplate.update("INSERT INTO book(NAME, AUTHOR, YEAR_OF_PUBLISHING) VALUES(?, ?, ?)",
                book.getName(), book.getAuthor(), book.getYearOfPublishing());
    }

    /**
     * Метод обновляет данные о книге в базе данных по её ID.
     * @param bookId - ID книги.
     * @param updatedBook - книга с новыми данными.
     */
    public void update(int bookId, Book updatedBook){
        jdbcTemplate.update("UPDATE book SET name=?, author=?, year_of_publishing=? WHERE book_id=?",
                updatedBook.getName(), updatedBook.getAuthor(),
                updatedBook.getYearOfPublishing(), bookId);
    }

    /**
     * Метод, удаляющий книгу из БД по её id.
     * @param bookId - ID книги.
     */
    public void delete(int bookId){
        jdbcTemplate.update("DELETE FROM book WHERE book_id=?", bookId);
    }

    /**
     * Метод, проверяющий, принадлежит ли книга определенному человеку.
     * @param bookId - ID книги.
     * @return true, если книга принадлежит человеку, и false в противном случае
     */
    public boolean ownershipCheck(int bookId){
        Integer count = jdbcTemplate.queryForObject("SELECT person_id FROM book WHERE book_id=?",
                new Object[]{bookId}, Integer.class);
        return count != null;
    }

    /**
     * Метод, возвращающий информацию о человеке, которому принадлежит книга.
     * @param bookId - ID книги.
     * @return объект Person, если книга принадлежит человеку, и null в противном случае
     */
    public Person showPerson(int bookId){
        if(!ownershipCheck(bookId)){
            return null;
        }
        return jdbcTemplate.queryForObject("SELECT p.person_id, p.name, p.year_of_birth " +
                        "FROM Person p JOIN Book b ON p.person_id = b.person_id " +
                        "WHERE b.book_id=?", new Object[]{bookId},
                new BeanPropertyRowMapper<>(Person.class));
    }

    /**
     * Метод, назначающий книгу определенному человеку.
     * @param personId - ID человека, которому нужно назначить книгу
     * @param bookId - ID книги, которую нужно назначить
     */
    public void assignOwner(int personId, int bookId){
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE book_id=?;",
                personId, bookId);
    }

    /**
     * Метод, освобождающий книгу от привязки к человеку.
     * @param bookId - ID книги.
     */
    public void release(int bookId){
        jdbcTemplate.update("UPDATE book SET person_id=NULL WHERE book_id=?", bookId);
    }

}
