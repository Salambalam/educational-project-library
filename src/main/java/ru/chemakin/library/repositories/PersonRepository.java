package ru.chemakin.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chemakin.library.model.Person;
@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
}
