package ru.chemakin.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.chemakin.library.dao.BookDAO;
import ru.chemakin.library.dao.PersonDAO;
import ru.chemakin.library.model.Book;
import ru.chemakin.library.model.Person;
import ru.chemakin.library.util.BookValidator;

@Controller
@RequestMapping("/book")
public class BookController {
    private final BookDAO bookDAO;
    private final BookValidator bookValidator;

    public BookController(BookDAO bookDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("book", bookDAO.index());
        return "book/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id){
        boolean condition = bookDAO.ownershipCheck(id);
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("condition", condition);
        if(condition){
            model.addAttribute("person", bookDAO.showPerson(id));
        }
        model.addAttribute("chosenPerson", new Person());
        model.addAttribute("people", bookDAO.indexPerson());
        return "book/show";
    }

    @PatchMapping("/appoint")
    public String appoint(@ModelAttribute("chosenPerson") Person person,
                          @ModelAttribute("book") Book book){
        bookDAO.assignOwner(person.getPerson_id(), book.getBookId());
        return "redirect:/book";
    }
}
