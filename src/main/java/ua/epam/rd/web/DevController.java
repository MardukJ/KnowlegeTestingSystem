package ua.epam.rd.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.epam.rd.domain.User;
import ua.epam.rd.repository.UserRepository;
import ua.epam.rd.web.tools.Benchmark;

/**
 * Created by Mykhaylo Gnylorybov on 25.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */

@Controller
public class DevController {

    @Autowired
    private UserRepository userRepository;

    //dev menu
    @RequestMapping(value = "/dev")
    @ResponseBody
    public String body(Model model) {
        String menu = "<a href=/dev/user_gen> add 100_000 users </a>";
        return menu;
    }

    @RequestMapping(value = "/dev/user_gen")
    @ResponseBody
    public String zasriUserami(Model model) {
        Benchmark bm = new Benchmark();
        bm.start();

        User user;

        //admin+tester
        if (userRepository.getTotalEntry() == 0) {
            user = new User();
            user.setEmail("admin");
            user.setPassword("12345");
            user.setBlocked(Boolean.FALSE);
            userRepository.add(user);
            user = new User();
            user.setEmail("tester");
            user.setPassword("12345");
            user.setBlocked(Boolean.FALSE);
            userRepository.add(user);
        }

        //blank users
        for (int i = 0; i < 1000; i++) {
            user = new User();
            user.setEmail("u" + i + "@mail.com");
            user.setPassword("12345");
            user.setBlocked(Boolean.TRUE);
            userRepository.add(user);
        }

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return String.valueOf(bm.getDifferce());
    }
}
