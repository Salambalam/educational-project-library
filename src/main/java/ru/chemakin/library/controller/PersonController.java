package ru.chemakin.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.chemakin.library.dao.PersonDAO;

@Controller
@RequestMapping("/people")
public class PersonController {
    private PersonDAO personDAO;
    private Validator validator;

    public PersonController(PersonDAO personDAO, Validator validator) {
        this.personDAO = personDAO;
        this.validator = validator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }


}
