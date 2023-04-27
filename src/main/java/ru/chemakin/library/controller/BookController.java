package ru.chemakin.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.chemakin.library.dao.BookDAO;
import ru.chemakin.library.model.Book;
import ru.chemakin.library.model.Person;
import ru.chemakin.library.util.BookValidator;

import javax.validation.Valid;

/** Класс контроллер, обрабатывает запрос "/book" **/
@Controller
@RequestMapping("/book")
public class BookController {
    private final BookDAO bookDAO; // Поле, хранящее ссылку на объект BookDAO для работы с БД
    private final BookValidator bookValidator;// Поле, хранящее ссылку на объект BookValidator для валидации входящих данных и обработки ошибок

    /**
     * Коструктор  BookController внедряет BookDAO и BookValidator
     * @param bookDAO - для работы с БД
     * @param bookValidator - для проведения валидации
     */
    public BookController(BookDAO bookDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
    }

    /**
     * метод принимает все запросы на /book
     * и возвращает представление "book/index" передавая в него при этом список всех книг
     */
    @GetMapping
    public String index(Model model){
        model.addAttribute("book", bookDAO.index());
        return "book/index";
    }

    /**
     * метод передает в представление объект Book
     * выполняет проверку на принадлежность,
     * если принадлежит также передает Person
     * в обратном случае передет список из всех людей для назначения книги
     * @return представление "/book/show"
     */
    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id,
                       @ModelAttribute("chosenPerson") Person person){
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("condition", bookDAO.ownershipCheck(id));
        model.addAttribute("person", bookDAO.showPerson(id));
        model.addAttribute("people", bookDAO.indexPerson());
        return "book/show";
    }

    /**
     * метод делает редирект на страницу книги с переданным ид
     * назначает книгу внедренному человеку по его ID
     */
    @PatchMapping("/appoint/{id}")
    public String appoint(@ModelAttribute("chosenPerson") Person person,
                          @PathVariable("id") Integer id){
        bookDAO.assignOwner(person.getPerson_id(), id);
        return "redirect:/book/" + id;
    }

    /**
     * Принимает ID книги в базе данных и удаляет ее из базы данных.
     * Затем он перенаправляет пользователя на представление "redirect:/book".
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/book";
    }

    /**
     * Метод освобождает книгу (ставит null во вторичный ключ)
     * @param id - параметр передаваемый из запроса, ID книги
     * @return - перенаправляет пользователя на страницу выбранной книги "/book/ID"
     */
    @PatchMapping("/release")
    public String release(@RequestParam("id") int id){
        bookDAO.release(id);
        return "redirect:/book/" + id;
    }

    /**
     * Метод перенаправляет пользователя на форму редактирования книги.
     * @param id - параметр передаваемый из запроса, ID редактируемой книги
     * @return - форма "/book/edit"
     */
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookDAO.show(id));
        return "/book/edit";
    }

    /**
     * Принимает PATCH запрос, который содержит форму, заполненную ползователем, для изменения данных о книге и передачи их в БД
     * @param id - параметр передаваемый из запроса, ID редактируемой книги
     * @param book - объект, полученный из передаваемой формы
     * @param bindingResult - объект для управления отображения ошибок валидации
     * @return - перенаправляет пользователя на страницу измененной книги "/book/ID"
     */
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors()){
            return "book/edit";
        }
        bookDAO.update(id, book);
        return "redirect:/book/" + id;
    }

    /**
     * Направляет пользователя на форму создания книги, внедряя пустой объект Book,
     * поля которого заполнит пользователь в форме
     * @param book - внедремый объект для заполнения полей
     * @return - направляет пользователя на форму создания книги "book/new"
     */
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "book/new";
    }

    /**
     * Принимает все POST запросы на адресс "/book".
     * Проверяет входящий объект из формы на прохождение валидацииБ,
     * если нет ошибок добавлет его в БД
     * @param book - параметр полученный из формы. Book поля которого заполнил пользователь
     * @param bindingResult - бъект для управления отображения ошибок валидации
     * @return - перенаправляет пользователя на страницу "/book"
     */
    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "book/new";
        }
        bookDAO.save(book);
        return "redirect:/book";
    }
}
