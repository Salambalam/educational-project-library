//package ru.chemakin.library.dao;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import ru.chemakin.library.model.Book;
//import ru.chemakin.library.model.Person;
//
//import java.util.List;
//
//@Component
//public class PersonDAO {
////    private final SessionFactory sessionFactory;
////    @Autowired
////    public PersonDAO(SessionFactory sessionFactory) {
////
////        this.sessionFactory = sessionFactory;
////    }
////
////
////    /**
////     * Метод извлекает всю таблицу Person из БД
////     * @return - ArrayList со всеми людьми
////     */
////    @Transactional(readOnly = true)
////    public List<Person> index(){
////        Session session = sessionFactory.getCurrentSession();
////        return session.createQuery("SELECT p FROM Person p", Person.class).getResultList();
////    }
////
////    /**
////     *
////     * @param id - id человека к которому необходимо выполнить запрос
////     * @return - Person по переданному id
////     */
////    @Transactional(readOnly = true)
////    public Person show(int id){
////        Session session = sessionFactory.getCurrentSession();
////        return session.get(Person.class, id);
////    }
////
////
////    public List<Book> showPeopleBook(int personId){
////        Session session = sessionFactory.getCurrentSession();
////
////        session.createQuery("SELECT * FROM ")
////        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?", new Object[]{personId},
////                new BeanPropertyRowMapper<>(Book.class));
////    }
////
////    /**
////     * Метод добавляет человека в таблицу
////     * @param person - человек, которого необходимо поместить в БД
////     */
////    @Transactional
////    public void save(Person person){
////        Session session = sessionFactory.getCurrentSession();
////
////        session.save(person);
////    }
////
////
////    /** метод обновляет Person **/
////    @Transactional
////    public void update(int id, Person updatedPerson){
////        Session session = sessionFactory.getCurrentSession();
////
////        Person person = session.get(Person.class, id);
////        person.setName(updatedPerson.getName());
////        person.setYearOfBirth(updatedPerson.getYearOfBirth());
////
////        session.update(person);
////    }
////
////    /**
////     * Метод удаляет Person по id
////     * @param id - id человека, которого нужно удалить
////     */
////    @Transactional
////    public void delete(int id){
////        Session session = sessionFactory.getCurrentSession();
////        session.remove(session.get(Person.class, id));
////    }
////
////    /**
////     * Метод проверяет есть ли в таблице Book(в вторичном ключе) значение первичного ключа(Person)
////     * @param id - id человека, которого необходимо проверить
////     * @return - true(елси есть совпадения) | false(если нет совпадеинй)
////     */
////    @Transactional(readOnly = true)
////    public boolean checkFK(int id){
////        Session session = sessionFactory.getCurrentSession();
////        Integer result = session.createQuery("SELECT COUNT(*) FROM Book WHERE person_id = :personId", Integer.class)
////                .setParameter("personId", id)
////                .uniqueResult();
////        return result != null;
////    }
//
//}