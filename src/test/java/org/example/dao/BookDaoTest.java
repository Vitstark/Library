package org.example.dao;

import org.example.configurations.TestConfiguration;
import org.example.models.Book;
import org.example.models.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Optional;

public class BookDaoTest {
    private static BookDAO bookDAO;
    private static Book cleanCode;
    private static Book cleanArchitecture;
    private static Book javaDevelopment;

    @BeforeAll
    public static void initMethod() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfiguration.class);
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        bookDAO = new BookDAOImpl(dataSource);

        cleanCode = new Book(1l, "Чистый код", "Боб Мартин", 2013, 1l);
        cleanArchitecture = new Book(2l, "Чистая архитектура", "Боб Мартин", 2016, 0l);
        javaDevelopment = new Book(3l, "Разработка приложений на Java", "Боб Мартин", 2006, 0l);
    }

    @Test
    public void getBookByIdShouldCorrect() {
        Assertions.assertEquals(cleanCode, bookDAO.findByID(1l).get());
        Assertions.assertEquals(cleanArchitecture, bookDAO.findByID(2l).get());
        Assertions.assertEquals(javaDevelopment, bookDAO.findByID(3l).get());
    }

    @Test
    public void findBooksOfReader() {
        Book [] books = new Book[] {cleanCode};
        Person person = new Person(1l, "Vasya", "01.01.2001");

        Assertions.assertArrayEquals(books, bookDAO.findBooksOfReaderOrderByName(person).toArray());
    }

    @Test
    public void saveAndDelete() {
        Book book = Book.builder()
                .name("TestBook")
                .author("Test Writer")
                .date(0)
                .personId(0l)
                .build();

        bookDAO.save(book);
        Assertions.assertEquals(book, bookDAO.findByID(book.getId()).get());

        bookDAO.delete(book.getId());
        Assertions.assertEquals(Optional.empty(), bookDAO.findByID(book.getId()));
    }

    @Test
    public void update() {
        cleanCode.setAuthor("nitraM boB");
        bookDAO.update(cleanCode);
        Assertions.assertEquals(cleanCode, bookDAO.findByID(cleanCode.getId()).get());

        cleanCode.setAuthor("Bob Martin");
        bookDAO.update(cleanCode);
        Assertions.assertEquals(cleanCode, bookDAO.findByID(cleanCode.getId()).get());
    }
}
