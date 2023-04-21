package ru.chemakin.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.chemakin.library.model.Book;
import ru.chemakin.library.model.Person;

import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    /** метод извлекает всю таблицу Person из БД **/
    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
        // query -  метод используется для выполнения запроса к базе данных и извлечения данных из нее.
        // BeanPropertyRowMapper - реализует RowMapper, который используется для преобразования строк БД в Java объекты

    }


    /** метод извлекает Person с переданным id, если такого нет, то выкинет ошибку **/
    public Person show(int id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
        // убрать orElse после добаления валидации
    }

    public List<Book> showPeopleBook(int personId){
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?", new Object[]{personId},
                new BeanPropertyRowMapper<>(Book.class));
    }


    /** метод добавляет Person в таблицу **/
    public void save(Person person){
        jdbcTemplate.update("INSERT INTO Person(name, year_of_birth) VALUE(?, ?)",
                person.getName(), person.getName());
        // update - метод для выполнения запроса на изменение данных в БД (добавление, обновление, удаление)
    }


    /** метод обновляет Person **/
    public void update(int id, Person updatedPerson){
        jdbcTemplate.update("UPDATE Peson SET name=?, year_of_birth=? WHERE id=?",
                updatedPerson.getName(), updatedPerson.getYearOfBirth(), id);
    }


    /** метод удаляет Person по id **/
    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    public boolean checkFK(int id){
        int a = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Book WHERE person_id = ?",
                new Object[]{id}, Integer.class);
        return a > 0;
    }

}