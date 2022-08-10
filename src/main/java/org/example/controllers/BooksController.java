package org.example.controllers;

import org.example.dao.BookDAO;
import org.example.dao.BookDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BookDAOImpl bookDAO;

    @GetMapping()
    public String books(Model model) {
        model.addAttribute("books", bookDAO.findAll());
        return "books/books";
    }
}
