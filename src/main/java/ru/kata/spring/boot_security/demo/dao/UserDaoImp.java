package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private final EntityManager entityManager;


    public UserDaoImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public void addUser(User user) {
        User newUser = new User(user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword());
        newUser.setRoles(user.getRoles());
        entityManager.persist(newUser);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = entityManager.createQuery("FROM User", User.class);
        return query.getResultList();
    }

    @Override
    public User getUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        User user = entityManager.find(User.class, id);
        if (user == null) {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("User with email " + email + " not found");
        }
    }

    @Override
    public void update(User user) {
        User existingUser = getUserById(user.getId());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setUsername(user.getUsername());
    }

    @Override
    public void delete(Long id) {
        User user = getUserById(id);
        entityManager.remove(user);
    }

}

