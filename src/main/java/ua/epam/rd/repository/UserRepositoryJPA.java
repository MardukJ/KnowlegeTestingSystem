package ua.epam.rd.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.rd.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 23.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 * <p>
 * UserRepository implementation
 * no data verification on repository level!
 */
@Repository
public class UserRepositoryJPA implements UserRepository {

    @PersistenceContext(name = "myPersistentUnit")
    EntityManager em;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long add(User user) {
        em.persist(user);
        return user.getId();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User getById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User getByMail(String email) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :mail", User.class);
        query.setParameter("mail", email);
        try {
            return query.getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<User> getAll() {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void merge(User user) {
        em.merge(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getTotalEntry() {
        Query queryTotal = em.createQuery
                ("SELECT COUNT (u) from User u");
        long countResult = (long) queryTotal.getSingleResult();
        return countResult;
    }

    @Override
    public List<User> getEntryInRange(int first, int size) {
        Query query = em.createQuery("FROM User");
        query.setFirstResult(first);
        query.setMaxResults(size);
        return query.getResultList();
    }
}
