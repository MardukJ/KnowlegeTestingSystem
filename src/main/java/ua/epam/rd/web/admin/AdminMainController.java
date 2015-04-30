package ua.epam.rd.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.epam.rd.domain.Group;
import ua.epam.rd.domain.User;
import ua.epam.rd.service.GroupService;
import ua.epam.rd.service.UserService;
import ua.epam.rd.web.tools.Benchmark;
import ua.epam.rd.web.tools.ExpressionFilter;
import ua.epam.rd.web.tools.SecurityManager;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 25.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
@Controller
public class AdminMainController {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "/admin/home") // always set method
    public String adminHome(HttpSession session, Model model) {
        if (!SecurityManager.isAdmin(session)) return "redirect:/*";

        Benchmark bm = new Benchmark();
        bm.start();

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return "admin/admin_home";
    }


}
