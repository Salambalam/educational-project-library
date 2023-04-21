package ru.chemakin.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.chemakin.library.dao.PersonDAO;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonDAO personDAO;

    public PersonController(PersonDAO personDAO) {
        this.personDAO = personDAO;

    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("person", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("people", personDAO.show(id));
        model.addAttribute("book", personDAO.showPeopleBook(id));
        model.addAttribute("id", checkLink(id)); // добавить person_id в модель
        return "people/show";
    }

    @ModelAttribute("checkFK")
    public boolean checkLink(int id){
        return personDAO.checkFK(id);
    }

}
