package dao;

public interface CRUD<ID,T> {
    void save(T entity);
    void findByID(ID id);
    void update(T entity);
    void delete(ID id);
}
