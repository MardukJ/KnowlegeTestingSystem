package ua.epam.rd;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.epam.rd.repository.ExamRepository;
import ua.epam.rd.repository.GroupRepository;
import ua.epam.rd.repository.QuestionRepository;
import ua.epam.rd.repository.UserRepository;
import ua.epam.rd.service.*;

import ua.epam.rd.domain.User;

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

        ExamRepository examRepository = appContext.getBean(ExamRepository.class, "examRepository");
        ExamService examService = appContext.getBean(ExamService.class, "examService");

        InviteService inviteService = appContext.getBean(InviteService.class, "inviteService");

        User user = new User();
        user.setEmail("a@b.ru");
        System.out.println(user.verifyMail());

        user.setEmail("bob.marley@dot.com");
        System.out.println(user.verifyMail());

        user.setEmail("vasya@a.hren.znaem.gde.ru");
        System.out.println(user.verifyMail());

        user.setEmail("a.b.ru");
        System.out.println(user.verifyMail());

        user.setEmail(".a@b.ru");
        System.out.println(user.verifyMail());

        user.setEmail(".a@ru");
        System.out.println(user.verifyMail());


        appContext.close();
    }
}
