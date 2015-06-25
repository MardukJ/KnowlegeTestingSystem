package ua.epam.rd.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.rd.domain.Invite;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 10.05.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
@Repository
public class InviteRepostioryJPA implements InviteRepostiory {
    @PersistenceContext(name = "myPersistentUnit")
    EntityManager em;

    @Override
    @Transactional (propagation = Propagation.REQUIRED)
    public Long add(Invite invite) {
        em.persist(invite);
        return invite.getId();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Invite getById(Long id) {
        Invite result = em.find(Invite.class, id);
        return result;
    }

    @Override
    @Transactional (propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Invite> getAll() {
        TypedQuery <Invite> query = em.createQuery("SELECT i from Invite i", Invite.class);
        return query.getResultList();
    }

    @Override
    @Transactional (propagation = Propagation.REQUIRED)
    public void merge(Invite invite) {
        em.merge(invite);
    }

    @Override
    @Transactional (propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }
}
