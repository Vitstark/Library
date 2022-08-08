package dao;

import models.Book;
import models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class BookDAOImpl implements BookDAO {

    private static RowMapper<Book> bookMapper = (row, rowNumber) -> Book.builder()
            .id(row.getLong("id"))
            .name(row.getString("name"))
            .author(row.getString("author"))
            .date(row.getString("year_of_create"))
            .build();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Book> findBooksOfReaderOrderByName(Person person) {
        return jdbcTemplate.query("", bookMapper);
    }

    public void save(Book entity) {

    }

    public void findByID(Long aLong) {

    }

    public void update(Book entity) {

    }

    public void delete(Long aLong) {

    }
}
