package ru.chemakin.library.servises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chemakin.library.model.Book;
import ru.chemakin.library.model.Person;
import ru.chemakin.library.repositories.BookRepository;
import ru.chemakin.library.repositories.PersonRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public Book findOne(int id){
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> findByName(String name){
        return bookRepository.findBookByNameContainsIgnoreCase(name);
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
        if(book.getPersonId() != null) {
            return personRepository.findById(book.getPersonId()).orElse(null);
        }else{
            return null;
        }
    }

    @Transactional
    public void setPersonId(Person person, int bookId){
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book != null) {
            book.setPersonId(person.getPersonId());
            System.out.println(book.getPersonId());
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

    public List<Book> getListOfBooks(Integer countPage, Integer booksPerPage, String sortByYear) {
        if(sortByYear != null && sortByYear.equals("true")){
            if(countPage != null && booksPerPage != null){
                return partOfBooks(countPage, booksPerPage, sortBooks(bookRepository.findAll()));
            }
            return sortBooks(bookRepository.findAll());
        }else{
            if(countPage == null || booksPerPage == null){
                return bookRepository.findAll();
            }
            return partOfBooks(countPage, booksPerPage, bookRepository.findAll());
        }
    }

    private List<Book> partOfBooks(Integer countPage, Integer booksPerPage, List<Book> books) {
        int startIndex = countPage * booksPerPage;
        int endIndex = Math.min(startIndex + booksPerPage, books.size());
        return books.subList(startIndex, endIndex);
    }


    private List<Book> sortBooks(List<Book> books){
        return books.stream().sorted(Comparator.comparingInt(Book::getYearOfPublishing)).collect(Collectors.toList());
    }
}
