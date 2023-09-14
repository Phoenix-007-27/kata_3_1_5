package ru.kata.spring.boot_security.demo.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@Repository
public class RoleDaoImpl implements RoleDao {

    private EntityManager entityManager;

    @Autowired
    public RoleDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Role findById(int id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public List<Role> findAll() {
        return entityManager.createQuery("from Role ").getResultList();
    }

    @Override
    public Role findByName(String name) {
        Query query = entityManager.createQuery("SELECT a FROM Role a WHERE a.role like ?1");
        query.setParameter(1, name);
        return  (Role) query.getResultList().get(0);

    }


}
