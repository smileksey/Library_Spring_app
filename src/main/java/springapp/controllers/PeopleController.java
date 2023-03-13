package springapp.controllers;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springapp.dao.BookDAO;
import springapp.models.Book;
import springapp.models.Person;
import springapp.services.BooksService;
import springapp.services.PeopleService;
import springapp.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final BooksService booksService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, BooksService booksService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.booksService = booksService;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model) {
        //Получаем всех людей из БД и отправляем на отображение во view
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    //В строке запроса будет содержаться параметр id в виде целого числа
    //с помощью аннотации @PathVariable мы вытаскиваем этот параметр и присваеваем его переменной int id
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        //Получаем одного человека по id из БД и передаем на отображение во view
        Person person = peopleService.findOne(id);
        model.addAttribute("person", person);
        model.addAttribute("books", person.getBooks());

        return "people/show";
    }

    //нужно создать атрибут с нужным объектом, чтобы указать его в форме Thymeleaf (th:object="${person}")
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    //@Valid - чтобы значения полей, приходящие из формы, проходили валидацию
    //при внедрении в объект person
    //bindingResult - объект, хранящий ошибки валидации
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {

        if (bindingResult.hasErrors()) {
            return "people/edit";
        }

        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }

}
