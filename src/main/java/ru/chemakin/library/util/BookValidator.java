package ru.chemakin.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.chemakin.library.dao.BookDAO;
import ru.chemakin.library.model.Book;


/**
 * Этот класс используется для проведения валидации объектов Book.
 * Он проверяет, есть ли в БД объект с таким же названием, и выбрасывает ошибку, если это так.
 */
@Component
public class BookValidator implements Validator {

    private final BookDAO bookDAO;

    /**
     * Конструктор класса.
     * @param bookDAO DAO-объект, используемый для обращения к БД.
     */
    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    /**
     * Метод, который сообщает, поддерживает ли данный валидатор проверку указанного класса.
     * @param aClass класс, который должен быть проверен валидатором.
     * @return true, если класс Book, и false в противном случае.
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    /**
     * Метод, который проводит валидацию объекта Book.
     * @param object объект, который должен быть проверен.
     * @param errors объект, в который будут добавлены сообщения об ошибках.
     */
    @Override
    public void validate(Object object, Errors errors) {
        Book book = (Book) object;
        if (bookDAO.show(book.getName()).isPresent()) { // проверяем, есть ли в БД объект с таким же названием
            errors.rejectValue("name", "", "This Title is already taken."); // добавляем сообщение об ошибке, если такой объект есть
        }
    }
}
