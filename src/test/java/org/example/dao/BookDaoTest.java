package org.example.dao;

import org.example.configurations.TestConfiguration;
import org.example.models.Book;
import org.example.models.Person;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BookDaoTest {
    private static BookDAO bookDAO;
    private static JdbcTemplate jdbcTemplate;
    private static Book cleanCode;
    private static Book cleanArchitecture;
    private static Book javaDevelopment;
    private Book book;

    @BeforeAll
    public static void initMethod() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfiguration.class);
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        jdbcTemplate = new JdbcTemplate(dataSource);
        bookDAO = new BookDAOImpl(dataSource);

        cleanCode = new Book(1l, "Чистый код", "Боб Мартин", 2013, 1l);
        cleanArchitecture = new Book(2l, "Чистая архитектура", "Боб Мартин", 2016, 0l);
        javaDevelopment = new Book(3l, "Разработка приложений на Java", "Боб Мартин", 2006, 0l);
    }

    @BeforeEach
    public void initBook() {
        book = Book.builder()
                .name("TestBook")
                .author("Test Writer")
                .date(0)
                .build();

        Map<String, Object> params = new HashMap<>();

        params.put("name", book.getName());
        params.put("author", book.getAuthor());
        params.put("year_of_create", book.getDate());

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);

        Long id = insert.withTableName("book")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new MapSqlParameterSource(params))
                .longValue();

        book.setId(id);
    }

    @Test
    public void getBookByIdShouldCorrect() {
        book.setPersonId(0l);

        Assertions.assertEquals(cleanCode, bookDAO.findByID(1l).get());
        Assertions.assertEquals(cleanArchitecture, bookDAO.findByID(2l).get());
        Assertions.assertEquals(javaDevelopment, bookDAO.findByID(3l).get());
        Assertions.assertEquals(book, bookDAO.findByID(book.getId()).get());
    }

    @Test
    public void findBooksOfReader() {
        Book [] books = new Book[] {cleanCode};
        Person person = new Person(1l, "Vasya", "01.01.2001");

        Assertions.assertArrayEquals(books, bookDAO.findBooksOfReaderOrderByName(person).toArray());
    }

    @Test
    public void bookShouldBeSaved() {
        bookDAO.save(book);
        book.setPersonId(0l);

        Assertions.assertNotEquals(null, book.getId());
        Assertions.assertEquals(book, bookDAO.findByID(book.getId()).get());
    }

    @Test
    public void bookShouldBeDeleted() {
        bookDAO.save(book);
        bookDAO.delete(book.getId());

        Assertions.assertEquals(Optional.empty(), bookDAO.findByID(book.getId()));
    }

    @Test
    public void updateShouldChangeBook() {
        book.setName("kooBtseT");
        book.setAuthor("nitraM boB");
        book.setDate(20002);
        book.setPersonId(2l);
        bookDAO.update(book);

        Book updatedBook = bookDAO.findByID(book.getId()).get();

        Assertions.assertEquals(book.getName(), updatedBook.getName());
        Assertions.assertEquals(book.getAuthor(), updatedBook.getAuthor());
        Assertions.assertEquals(book.getDate(), updatedBook.getDate());
        Assertions.assertEquals(book.getPersonId(), updatedBook.getPersonId());
    }

    @AfterEach
    public void deleteBook() {
        jdbcTemplate.update("delete from book where id = " + book.getId());
    }
}
