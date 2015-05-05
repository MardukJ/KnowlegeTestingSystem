package ua.epam.rd.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.rd.domain.Exam;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 05.05.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
@Repository
public class ExamRepositoryJPA implements ExamRepository {

    @PersistenceContext(name = "myPersistentUnit")
    EntityManager em;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long add(Exam exam) {
        em.persist(exam);
        return exam.getId();
    }

    @Override
    @Transactional (propagation = Propagation.SUPPORTS, readOnly = true)
    public Exam getById(Long id) {
        return em.find(Exam.class, id);
    }

    @Override
    @Transactional (propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Exam> getAll() {
        TypedQuery<Exam> query = em.createQuery("SELECT e FROM Exam e", Exam.class);
        return query.getResultList();
    }

    @Override
    @Transactional (propagation = Propagation.REQUIRED)
    public void merge(Exam exam) {
        em.merge(exam);
    }

    @Override
    @Transactional (propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }
}
