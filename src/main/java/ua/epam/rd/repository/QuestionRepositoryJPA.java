package ua.epam.rd.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.rd.domain.Question;
import ua.epam.rd.domain.QuestionAnswerOption;

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
        question.removeVoidOptions();
        em.persist(question);
        //BUG: не работает каскад, приходится руками
        boolean needMerge = false;
        for (QuestionAnswerOption qao: question.getOptions()) {
            if (qao.getQuestionForOption()==null) {
                qao.setQuestionForOption(question);
                needMerge = true;
            }
        }
        if (needMerge) em.merge(question);
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
    @Transactional(propagation = Propagation.REQUIRED)
    public void merge(Question question) {
        question.removeVoidOptions();
        for (QuestionAnswerOption qao: question.getOptions()) {
            if (qao.getQuestionForOption()==null) {
                qao.setQuestionForOption(question);
            }
        }
        em.merge(question);
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Question> getAllValidByGroup(Long idGroup) {
        TypedQuery <Question> typedQuery = em.createQuery("SELECT q FROM Question q WHERE q.groupOfQuestion.id_group = :idGroup AND q.outdated = false", Question.class);
        System.out.println(idGroup);
       typedQuery.setParameter("idGroup",idGroup);
        return typedQuery.getResultList();
    }
}
