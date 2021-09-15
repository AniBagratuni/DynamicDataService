package com.task.demo.dao.repositories;

public interface GenericRepository {

    void save(Object data);

    void update(Object data);

    void delete(Class cls, int id);

    Object findById(Class cls, int id);
}
