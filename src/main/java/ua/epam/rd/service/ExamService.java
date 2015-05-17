package ua.epam.rd.service;

import ua.epam.rd.domain.Exam;

import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 06.05.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
public interface ExamService {
    public Long saveNewExam(Exam exam);

    public Exam getById(Long idExam);

    public List <Exam> getExamsOfUser(Long idUser);

    public Exam checkTimeoutAndStatus(Long idExam);
}
