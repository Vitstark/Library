package org.example.controllers;

import org.example.dao.BookDAO;
import org.example.dao.BookDAOImpl;
import org.example.dao.PersonDAOImpl;
import org.example.models.Book;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BookDAOImpl bookDAO;
    @Autowired
    private PersonDAOImpl personDAO;

    @GetMapping()
    public String books(Model model) {
        model.addAttribute("books", bookDAO.findAll());
        return "books/books";
    }

    @GetMapping("/{id}")
    public String book(@PathVariable("id") Long id, @ModelAttribute("person") Person person, Model model) {
        Book book = bookDAO.findByID(id).get();
        model.addAttribute("book", book);
        model.addAttribute("reader", personDAO.findByID(book.getPersonId()));
        model.addAttribute("people", personDAO.findAllOrderedByName());
        return "books/book";
    }

    @PatchMapping("/{id}/free_book")
    public String freeBook(@PathVariable("id") Long id) {
        Book book = bookDAO.findByID(id).get();

        book.setPersonId(null);
        bookDAO.update(book);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/appoint_book")
    public String appointBook(@PathVariable("id") Long bookId, @ModelAttribute("person") Person person) {
        Book book = bookDAO.findByID(bookId).get();

        book.setPersonId(person.getId());
        bookDAO.update(book);
        return "redirect:/books/{id}";
    }
}
