package ua.epam.rd.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.epam.rd.service.UserService;
import ua.epam.rd.web.tools.*;
import ua.epam.rd.web.tools.SecurityManager;

import javax.servlet.http.HttpSession;

/**
 * Created by Mykhaylo Gnylorybov on 26.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    SecurityManager securityManager;

    @RequestMapping(value = "/home")
    public String home(HttpSession session, Model model) {
        if (ua.epam.rd.web.tools.SecurityManager.notLoggedIn(session)) return "redirect:/*";

        Benchmark bm = new Benchmark();
        bm.start();

        String view;
        model.addAttribute("msg", "Welcome " + SecurityManager.getLogin(session));

        if (securityManager.isTeacher(session)) {
            view="/teacher/home_teacher";
        } else {
            view="/user/home_user";
        }

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return view;
    }
}
