package dao;

import models.Book;
import models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

public class PersonDAOImpl implements PersonDAO {

    private static RowMapper<Person> personMapper = (rs, rowNum) ->  Person.builder()
            .id(rs.getLong("id"))
            .name(rs.getString("name"))
            .dateOfBirth(rs.getString("date_of_birth"))
            .build();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Person entity) {

    }

    public Optional<Book> findByID(Long aLong) {
        return null;
    }

    public void update(Person entity) {

    }

    public void delete(Long aLong) {

    }

    public List<Person> findAllOrderedByName() {
        return null;
    }
}
