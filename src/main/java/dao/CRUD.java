package dao;

import models.Book;

import java.util.Optional;

public interface CRUD<ID,T> {
    void save(T entity);
    Optional<Book> findByID(ID id);
    void update(T entity);
    void delete(ID id);
}
