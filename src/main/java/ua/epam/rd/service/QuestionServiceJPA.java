package ua.epam.rd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.rd.domain.Group;
import ua.epam.rd.domain.Question;
import ua.epam.rd.repository.GroupRepository;
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
    @Autowired
    GroupRepository groupRepository;

    @Override
    public Question getById(long id) {
        Question result =questionRepostitory.getById(id);
//        result.getGroupOfQuestion().getId();
        return result;
    }

    @Override
    public List<Question> getAll() {
        return questionRepostitory.getAll();
    }

    @Override
    @Transactional (propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Question> getAllValidByGroup(String nameOfGroup) {
        Group group = groupRepository.getByName(nameOfGroup);
        Long idGroup = group.getId();
        return questionRepostitory.getAllValidByGroup(idGroup);
    }

    @Override
    @Transactional (propagation = Propagation.REQUIRED)
    public void delete(long id) {
        Question question = questionRepostitory.getById(id);
        if (question==null) throw new IllegalArgumentException("Question not found");
        question.invalidate();
        questionRepostitory.merge(question);
    }

    @Override
    @Transactional (propagation = Propagation.SUPPORTS, readOnly = true)
    public String getGroupNameForQuestion(long id) {
        Question question = questionRepostitory.getById(id);
        if (question==null) throw new IllegalArgumentException("Question not found");
        if (question.getGroupOfQuestion()==null) throw new IllegalArgumentException("Lost question");
        return question.getGroupOfQuestion().getGroupName();
    }

    @Override
    @Transactional (propagation = Propagation.REQUIRED)
    public Question updateVersion(Question question) {
        Long id = question.getId();
        if (id==null) throw new IllegalArgumentException("Invalid question ID");
        Question oldQuestion = questionRepostitory.getById(id);
        if (oldQuestion==null) throw new IllegalArgumentException("Invalid question ID");
        String error = question.verifyMe();
        if (error!=null) throw new IllegalArgumentException("Invalid question format");
        oldQuestion.invalidate();
        question.setId(null);
        question.setVersion(oldQuestion.getVersion()+1L);
        question.setGroupOfQuestion(oldQuestion.getGroupOfQuestion());
        questionRepostitory.merge(oldQuestion);
        questionRepostitory.add(question);
        return question;
    }

    @Override
    @Transactional (propagation = Propagation.REQUIRED)
    public Question saveNewQuestion(Question question, String groupName) {
        if (question==null) throw new IllegalArgumentException("No question to save");
        String error = question.verifyMe();
        if (error!=null) throw new IllegalArgumentException("Invalid question format");
        Group group = groupRepository.getByName(groupName);
        if (group==null) throw new IllegalArgumentException("Invalid group");

        question.setGroupOfQuestion(group);
        questionRepostitory.add(question);
        return question;
    }
}
