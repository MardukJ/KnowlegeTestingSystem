package ua.epam.rd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.rd.domain.Question;
import ua.epam.rd.repository.QuestionRepository;

import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 02.05.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
@Service
public interface QuestionService {
    Question getById(long id);

    List<Question> getAll();

    List<Question> getAllValidByGroup(String nameOfGroup);

    void delete(long id);

    String getGroupNameForQuestion(long id);

    Question updateVersion(Question question);

    public Question saveNewQuestion(Question question, String groupName);

}