package ru.chemakin.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.chemakin.library.dao.PersonDAO;
import ru.chemakin.library.model.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonDAO personDAO;

    public PersonController(PersonDAO personDAO) {
        this.personDAO = personDAO;

    }

    /** возвращает список всех людей в базе данных,
     *  добавляет список людей в модель и возвращает представление "people/index" **/
    @GetMapping
    public String index(Model model){
        model.addAttribute("person", personDAO.index());
        return "people/index";
    }

    /** принимает ID человека в базе данных и возвращает информацию о нем,
     *  добавляет информацию о человеке и книгах, которые находятся у него на руках в модель
     *  и возвращает представление "people/show". **/
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("people", personDAO.show(id));
        model.addAttribute("book", personDAO.showPeopleBook(id));
        model.addAttribute("condition", personDAO.checkFK(id)); // добавить person_id в модель
        return "people/show";
    }

    /** принимает ID человека в базе данных и удаляет его из базы данных.
     *  Затем он перенаправляет пользователя на представление "redirect:/people". **/
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/people";
    }

    /** принимает ID человека в базе данных и возвращает представление "people/edit",
     *  которое позволяет пользователю отредактировать информацию о человеке. **/
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        //PathVariable используется для передачи нужного значения из запроса
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    /** принимает id и вызывает метод для обновления данных человека в БД
     *  Если в процессе валидации данных возникают ошибки, метод возвращает представление "people/edit",
     *  иначе он обновляет информацию о человеке в базе данных с помощью метода update() объекта personDAO и
     *  перенаправляет пользователя на представление "redirect:/people". **/
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") int id){
        //ModelAttribute принимает объект персон из формы и привязывает его к person
        //PathVariable принимает значение из адреса
        if(bindingResult.hasErrors()){
            return "people/edit";
        }
        personDAO.update(id, person);
        return "redirect:/people";
    }

    /** Метод добавит в модель объект Person с именем "person" и вернет представление "people/new". **/
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        // @ModelAttribute("person") Person person указывает,
        // что в модель представления будет добавлен объект типа Person с именем "person"
        return "people/new";
    }

    /** метод вызывается после того как пользователь заполняет и отправляет форму, метод получит объект Person из модели,
     *  выполнит валидацию и сохранит его в базе данных, если ошибок не найдено **/
    @PostMapping() //данный метод будет обрабатывать POST запрос по адресу, указанному в аннотации @RequestMapping на уровне класса
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult){
        // @ModelAttribute("person") @Valid Person person означает,
        // что объект Person будет получен из модели представления (которая заполняется в методе newPerson())
        //personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors()){
            return "people/new";
        }
        personDAO.save(person);
        return "redirect:/people";
    }
}
