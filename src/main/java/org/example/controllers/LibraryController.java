package org.example.controllers;

import org.example.dao.BookDAO;
import org.example.dao.PersonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/people")
public class LibraryController {

    @Autowired
    private PersonDAO personDAO;
    @Autowired
    private BookDAO bookDAO;

    @ModelAttribute("title")
    public String title() {
        return "People";
    }

    @GetMapping()
    public String getAllPerson(Model model) {
        model.addAttribute("people", personDAO.findAllOrderedByName());
        return "people/people";
    }
}
