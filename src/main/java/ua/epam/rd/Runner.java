package ua.epam.rd;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.epam.rd.domain.Question;
import ua.epam.rd.domain.QuestionAnswerOption;
import ua.epam.rd.repository.GroupRepository;
import ua.epam.rd.repository.QuestionRepository;
import ua.epam.rd.repository.UserRepository;
import ua.epam.rd.service.GroupService;
import ua.epam.rd.service.QuestionService;
import ua.epam.rd.service.UserService;

/**
 * Created by Mykhaylo Gnylorybov on 23.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
public class Runner {
    public static void main(String arg[]) {
        System.out.println("hello");
        ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:/SpringConfig.xml");
        UserRepository userRepository = appContext.getBean(UserRepository.class, "userRepository");
        UserService userService = appContext.getBean(UserService.class, "userService");

        GroupRepository groupRepository = appContext.getBean(GroupRepository.class, "groupRepository");
        GroupService groupService = appContext.getBean(GroupService.class, "groupService");

        QuestionRepository questionRepository = appContext.getBean(QuestionRepository.class, "questionRepository");
        QuestionService questionService = appContext.getBean(QuestionService.class, "questionService");


        System.out.println("LIST = " + userRepository.getEntryInRangeWithFilter(0, 10, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, "u10%"));

        Question question = new Question();
        question.setBody("i=5;\ni=++i + ++i;\ni=?");
        question.setTeacherComment("Brainfuck");
        question.setReviewComment("i=6+7=13");

        QuestionAnswerOption questionAnswerOption= new QuestionAnswerOption();
        questionAnswerOption.setOptionText("11");
        questionAnswerOption.setCorrectAnswer(Boolean.FALSE);
        question.getOptions().add(questionAnswerOption);

        questionAnswerOption= new QuestionAnswerOption();
        questionAnswerOption.setOptionText("12");
        questionAnswerOption.setCorrectAnswer(Boolean.FALSE);
        question.getOptions().add(questionAnswerOption);

        questionAnswerOption= new QuestionAnswerOption();
        questionAnswerOption.setOptionText("13");
        questionAnswerOption.setCorrectAnswer(Boolean.TRUE);
        question.getOptions().add(questionAnswerOption);

        questionAnswerOption= new QuestionAnswerOption();
        questionAnswerOption.setOptionText("14");
        questionAnswerOption.setCorrectAnswer(Boolean.FALSE);
        question.getOptions().add(questionAnswerOption);

        questionAnswerOption= new QuestionAnswerOption();
        questionAnswerOption.setOptionText("10");
        questionAnswerOption.setCorrectAnswer(Boolean.FALSE);
        question.getOptions().add(questionAnswerOption);


        questionRepository.add(question);

        System.out.println(questionService.getAll());

        appContext.close();
    }
}
