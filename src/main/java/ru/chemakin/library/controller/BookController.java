package ru.chemakin.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.chemakin.library.model.Book;
import ru.chemakin.library.model.Person;
import ru.chemakin.library.servises.BookService;
import ru.chemakin.library.servises.PersonService;
import ru.chemakin.library.util.BookValidator;

import javax.validation.Valid;

/** Класс контроллер, обрабатывает запрос "/book" **/
@Controller
@RequestMapping("/book")
public class BookController {
    private final BookValidator bookValidator;// Поле, хранящее ссылку на объект BookValidator для валидации входящих данных и обработки ошибок

    private final BookService bookService;
    private final PersonService personService;
    /**
     * Коструктор  BookController внедряет BookDAO и BookValidator
     *
     * @param bookValidator - для проведения валидации
     */
    @Autowired
    public BookController(BookValidator bookValidator, BookService bookService, PersonService personService) {
        this.bookValidator = bookValidator;
        this.bookService = bookService;
        this.personService = personService;
    }

    /**
     * метод принимает все запросы на /book
     * и возвращает представление "book/index" передавая в него при этом список всех книг
     */
    @GetMapping
    public String index(Model model){
        model.addAttribute("book", bookService.finAll());
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
        Book book = bookService.findOne(id);
        model.addAttribute("book", book);
        model.addAttribute("condition", !(book.getPersonId() == null));
        model.addAttribute("person", bookService.getOwnership(book));
        model.addAttribute("people", personService.findAll());
        return "book/show";
    }

    /**
     * метод делает редирект на страницу книги с переданным ид
     * назначает книгу внедренному человеку по его ID
     */
    @PatchMapping("/{id}/appoint") // мделать форму с скрытым полем
    public String appoint(@ModelAttribute("chosenPerson") Person person,
                          @PathVariable("id") Integer id){
        bookService.setPersonId(person, id);
        return "redirect:/book/" + id;
    }

    /**
     * Принимает ID книги в базе данных и удаляет ее из базы данных.
     * Затем он перенаправляет пользователя на представление "redirect:/book".
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookService.delete(id);
        return "redirect:/book";
    }

    /**
     * Метод освобождает книгу (ставит null во вторичный ключ)
     * @param id - параметр передаваемый из запроса, ID книги
     * @return - перенаправляет пользователя на страницу выбранной книги "/book/ID"
     */
    @PatchMapping("/release")
    public String release(@RequestParam("id") int id){
        bookService.setPersonId(id);
        return "redirect:/book/" + id;
    }

    /**
     * Метод перенаправляет пользователя на форму редактирования книги.
     * @param id - параметр передаваемый из запроса, ID редактируемой книги
     * @return - форма "/book/edit"
     */
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookService.findOne(id));
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
        bookService.update(id, book);
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
        bookService.save(book);
        return "redirect:/book";
    }
}
