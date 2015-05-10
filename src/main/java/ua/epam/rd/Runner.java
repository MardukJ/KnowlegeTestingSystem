package ua.epam.rd;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.rd.domain.*;
import ua.epam.rd.repository.ExamRepository;
import ua.epam.rd.repository.GroupRepository;
import ua.epam.rd.repository.QuestionRepository;
import ua.epam.rd.repository.UserRepository;
import ua.epam.rd.service.GroupService;
import ua.epam.rd.service.QuestionService;
import ua.epam.rd.service.UserService;

import javax.persistence.OneToMany;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.rd.domain.Group;
import ua.epam.rd.domain.User;
import ua.epam.rd.web.tools.Benchmark;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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

        Long userId = 378169L;//tester
//        Long userId = 378171L;//u1
//        Long userId = 378170L;//u0

        Benchmark bm = new Benchmark();
        bm.start();
        List result = questionRepository.tq(userId);
        bm.stop();
        System.out.println("time=" + bm.getDifferce());
        System.out.println(result.size());

        appContext.close();
    }
}
