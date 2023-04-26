package ru.chemakin.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.chemakin.library.dao.BookDAO;
import ru.chemakin.library.dao.PersonDAO;
import ru.chemakin.library.model.Book;
import ru.chemakin.library.model.Person;
import ru.chemakin.library.util.BookValidator;

import javax.validation.Valid;

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
        return "redirect:/book/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/book";
    }

    @PatchMapping("/release")
    public String release(@RequestParam("id") int id){
        bookDAO.release(id);
        return "redirect:/book/" + id;
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookDAO.show(id));
        return "/book/edit";
    }

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

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "book/new";
    }

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
