package ua.epam.rd.repository;

import ua.epam.rd.domain.Question;

import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 02.05.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
public interface QuestionRepository {
    Long add(Question question);

    Question getById (Long id);

    List<Question> getAll();

    void merge(Question question);

    void delete(Long id);

    List<Question> getAllValidByGroup(Long idGroup);

    public long getAllActiveByUserTotal(Long idUser);

    public List<Question> getAllActiveByUser(Long idUser, int first, int size);

    public List<Object> tq(Long idUser);
}
