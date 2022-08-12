package org.example.controllers;

import org.example.dao.BookDAO;
import org.example.dao.PersonDAO;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PersonDAO personDAO;
    @Autowired
    private BookDAO bookDAO;

    @ModelAttribute("title")
    public String title() {
        return "People";
    }

    @GetMapping()
    public String people(Model model) {
        model.addAttribute("people", personDAO.findAllOrderedByName());
        return "people/people";
    }

    @GetMapping("/{id}")
    public String person(@PathVariable Long id, Model model) {
        Person person = personDAO.findByID(id).get();
        model.addAttribute("person", person);
        model.addAttribute("booksOfPerson", bookDAO.findBooksOfReaderOrderByName(person));
        return "people/person";
    }
}
