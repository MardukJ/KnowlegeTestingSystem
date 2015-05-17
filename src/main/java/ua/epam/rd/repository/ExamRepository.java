package ua.epam.rd.repository;

import ua.epam.rd.domain.Exam;

import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 05.05.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
public interface ExamRepository {
    Long add(Exam exam);

    Exam getById (Long id);

    List<Exam> getAll();

    void merge(Exam exam);

    void delete(Long id);

    public List<Exam> getExamsOfUser(Long idUser);
}
