package org.example.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.example.configurations.TestConfiguration;
import org.example.models.Person;
import org.junit.jupiter.api.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PersonDAOImplTest {
    private static PersonDAOImpl personDAO;
    private static JdbcTemplate jdbcTemplate;
    private static Person andrew;
    private static Person boris;

    private Person person;

    @BeforeAll
    public static void initMethod() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfiguration.class);
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        jdbcTemplate = new JdbcTemplate(dataSource);
        personDAO = new PersonDAOImpl(dataSource);

        andrew = Person.builder()
                .id(1l)
                .name("Андреев Андрей Андреич")
                .dateOfBirth("2000.01.01")
                .build();

        boris = Person.builder()
                .id(2l)
                .name("Борисов Борис Борисович")
                .dateOfBirth("2003.02.12")
                .build();
    }

    @BeforeEach
    public void initPerson() {
        person = Person.builder()
                .name("TestPerson")
                .dateOfBirth("6666.66.66")
                .build();

        Map<String, Object> params = new HashMap<>();

        params.put("name", person.getName());
        params.put("date_of_birth", person.getDateOfBirth());

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);

        Long id = insert.withTableName("person")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new MapSqlParameterSource(params))
                .longValue();

        person.setId(id);
    }

    @Test
    public void findByIdShouldReturnCorrectPerson() {
        assertEquals(andrew, personDAO.findByID(andrew.getId()).get());
        assertEquals(boris, personDAO.findByID(boris.getId()).get());
        assertEquals(person, personDAO.findByID(person.getId()).get());
    }

    @Test
    public void findAllOrderedByNameShouldReturnSortedList() {
        Person[] people = new Person[]{person, andrew, boris};

        assertArrayEquals(people, personDAO.findAllOrderedByName().toArray());
    }

    @Test
    public void personShouldBeSaved() {
        Person testPerson = Person.builder()
                .name("TestPerson")
                .dateOfBirth("666.66.66")
                .build();

        personDAO.save(testPerson);

        assertNotEquals(null, testPerson.getId());
        assertEquals(testPerson, personDAO.findByID(testPerson.getId()).get());

        jdbcTemplate.update("DELETE FROM person WHERE id = ?", testPerson.getId());
    }

    @Test
    public void personShouldBeDeleted() {
        personDAO.delete(person.getId());

        assertEquals(Optional.empty(), personDAO.findByID(person.getId()));
    }

    @Test
    public void personShouldBeUpdated() {
        person.setName("BOB");
        person.setDateOfBirth("12.01.22");

        personDAO.update(person);

        Person updatedPerson = personDAO.findByID(person.getId()).get();

        assertEquals(person.getId(), updatedPerson.getId());
        assertEquals(person.getName(), updatedPerson.getName());
        assertEquals(person.getDateOfBirth(), updatedPerson.getDateOfBirth());
    }

    @AfterEach
    public void deleteTestPerson() {
        jdbcTemplate.update("delete from person where id = ?", person.getId());
    }

    @AfterAll
    public static void deleteAllTests() {
        jdbcTemplate.update("delete from person where name = 'TestPerson'");
    }
}
