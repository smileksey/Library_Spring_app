package springapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springapp.dao.BookDAO;
import springapp.dao.PersonDAO;
import springapp.models.Person;
import springapp.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final BookDAO bookDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, BookDAO bookDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model) {
        //Получаем всех людей из DAO и отправляем на отображение во view
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    //В строке запроса будет содержаться параметр id в виде целого числа
    //с помощью аннотации @PathVariable мы вытаскиваем этот параметр и присваеваем его переменной int id
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        //Получаем одного человека по id из DAO и передаем на отображение во view
        model.addAttribute("person", personDAO.show(id));
        model.addAttribute("books", bookDAO.personsBooks(id));
        return "people/show";
    }

    //Данный метод также можно реализовать с @ModelAttribute в аргументах,
    //не нужно будет вручную добавлять атрибут в модель через model.addAttribute
    @GetMapping("/new")
    public String newPerson(Model model) {
        //нужно создать атрибут с нужным объектом, чтобы указать его в форме Thymeleaf (th:object="${person}")
        model.addAttribute("person", new Person());
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
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {


        if (bindingResult.hasErrors()) {
            return "people/edit";
        }

        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }

}
