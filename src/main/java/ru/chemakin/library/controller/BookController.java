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
    public String show(Model model, @PathVariable("id") int id,
                       @ModelAttribute("chosenPerson") Person person){
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("condition", bookDAO.ownershipCheck(id));
        model.addAttribute("person", bookDAO.showPerson(id));
        model.addAttribute("people", bookDAO.indexPerson());
        return "book/show";
    }

    @PatchMapping("/appoint/{id}")
    public String appoint(@ModelAttribute("chosenPerson") Person person,
                          @PathVariable("id") Integer id){
        bookDAO.assignOwner(person.getPerson_id(), id);
        return "redirect:/book";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/book";
    }
}
