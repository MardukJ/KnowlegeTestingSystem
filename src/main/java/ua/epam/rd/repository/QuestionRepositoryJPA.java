package ua.epam.rd.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.rd.domain.Question;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 02.05.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
@Repository
public class QuestionRepositoryJPA implements QuestionRepository {
    @PersistenceContext(name = "myPersistentUnit")
    EntityManager em;

    @Override
    @Transactional (propagation = Propagation.REQUIRED)
    public Long add(Question question) {
        em.persist(question);
        return question.getId();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Question getById(Long id) {
        return em.find(Question.class, id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Question> getAll() {
        TypedQuery <Question> typedQuery = em.createQuery("SELECT q FROM Question q", Question.class);
        return typedQuery.getResultList();
    }

    @Override
    public void merge(Question question) {
        em.merge(question);
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }
}
