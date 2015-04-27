package ua.epam.rd.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.epam.rd.web.tools.Benchmark;
import ua.epam.rd.web.tools.SecurityManager;

import javax.servlet.http.HttpSession;

/**
 * Created by Mykhaylo Gnylorybov on 26.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
@Controller
public class UserController {

    @RequestMapping(value = "/home")
    public String home(HttpSession session, Model model) {
        Benchmark bm = new Benchmark();
        bm.start();

        if (ua.epam.rd.web.tools.SecurityManager.notLoggedIn(session)) return "redirect:/*";
        model.addAttribute("msg", "Welcome " + SecurityManager.getLogin(session));

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return "/home";
    }
}
