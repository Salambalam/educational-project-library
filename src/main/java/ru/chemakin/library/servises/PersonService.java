package ru.chemakin.library.servises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chemakin.library.model.Book;
import ru.chemakin.library.model.Person;
import ru.chemakin.library.repositories.BookRepository;
import ru.chemakin.library.repositories.PersonRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;
    private final BookRepository bookRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, BookRepository bookRepository) {
        this.personRepository = personRepository;
        this.bookRepository = bookRepository;
    }

    public List<Person> findAll(){
        return personRepository.findAll();
    }

    public Person findOne(int id){
        return personRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Person person){
        personRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setPersonId(id);
        personRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id){
        personRepository.deleteById(id);
    }

    public List<Book> getAllPersonBooks(int id){
        return bookRepository.findByPersonId(id);
    }

    public Map<Integer, Person> getPersonMap(List<Book> bookList) {
        Map<Integer, Person> personMap = new HashMap<>();
        for(Book book: bookList){
            if(book.getPersonId() != null){
                personMap.put(book.getPersonId(), findOne(book.getPersonId()));
            }
        }
        return personMap;
    }
}
