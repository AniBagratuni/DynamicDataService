package com.task.demo.dao.repositories.impl;

import com.task.demo.dao.repositories.GenericRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class GenericRepositoryImpl implements GenericRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void save(Object data) {
        entityManager.persist(data);
    }

    @Transactional
    @Override
    public void update(Object data) {
        entityManager.merge(data);
    }

    @Override
    @Transactional
    public void delete(Class cls, int id){
        Object obj = entityManager.find(cls, id);
        entityManager.remove(obj);
    }

    @Override
    public Object findById(Class cls, int id) {
        return entityManager.find(cls, id);
    }
}
