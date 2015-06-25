package ua.epam.rd;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.epam.rd.repository.ExamRepository;
import ua.epam.rd.repository.GroupRepository;
import ua.epam.rd.repository.QuestionRepository;
import ua.epam.rd.repository.UserRepository;
import ua.epam.rd.service.*;

import ua.epam.rd.domain.User;

import java.util.Random;

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

        Random rnd = new Random();
        for (int i=0;i<100;i++) {
            System.out.println(rnd.nextInt(2));
        }
        userRepository.add(new User());
        appContext.close();
    }
}
