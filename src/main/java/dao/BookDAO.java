package dao;

import models.Book;
import models.Person;

import java.util.List;

public interface BookDAO extends CRUD<Long, Book>{
    List<Book> findBooksOfReaderOrderByName(Person person);
}
