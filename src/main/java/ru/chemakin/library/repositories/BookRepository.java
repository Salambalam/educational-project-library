package ru.chemakin.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chemakin.library.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByPersonId(int id);
    List<Book> findBookByNameContainsIgnoreCase(String name);
}
