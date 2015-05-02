package ua.epam.rd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.rd.domain.Question;
import ua.epam.rd.repository.QuestionRepository;

import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 03.05.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
@Service
public class QuestionServiceJPA implements QuestionService {
    @Autowired
    QuestionRepository questionRepostitory;

    @Override
    public List<Question> getAll() {
        return questionRepostitory.getAll();
    }
}
