package ua.epam.rd;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.epam.rd.domain.User;
import ua.epam.rd.repository.UserRepository;
import ua.epam.rd.service.UserService;

/**
 * Created by Mykhaylo Gnylorybov on 23.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
public class Runner {
    public static void main (String arg[]) {
        System.out.println("hello");
        ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:/SpringConfig.xml");
        UserRepository userRepository = appContext.getBean(UserRepository.class, "userRepository");
        UserService userService = appContext.getBean(UserService.class, "userService");

        User user = new User();
        user.setEmail("123");
        user.setPassword("123");
        System.out.println(user);
        System.out.println(userRepository.add(user));
        System.out.println("size = " + userRepository.getAll().size());
        System.out.println("size = " + userRepository.getTotalEntry());
        System.out.println("pages = " + userService.getAllTotalPages());
        appContext.close();
    }
}
