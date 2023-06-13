package ru.chemakin.library.servises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chemakin.library.model.Book;
import ru.chemakin.library.model.Person;
import ru.chemakin.library.repositories.BookRepository;
import ru.chemakin.library.repositories.PersonRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    private final PersonRepository personRepository;
    @Autowired
    public BookService(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    public List<Book> finAll(){
        return bookRepository.findAll();
    }

    public Book findOne(int id){
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook){
        updatedBook.setBookId(id);
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        bookRepository.deleteById(id);
    }

    public Person getOwnership(Book book){
        return personRepository.findById(book.getPersonId()).orElse(null);
    }

    @Transactional
    public void setPersonId(Person person, int bookId){
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book != null) {
            book.setPersonId(person.getPersonId());
            bookRepository.save(book);
        }
    }
    @Transactional
    public void setPersonId(int bookId){
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book != null) {
            book.setPersonId(null);
            bookRepository.save(book);
        }
    }

}
