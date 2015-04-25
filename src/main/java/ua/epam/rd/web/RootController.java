package ua.epam.rd.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.epam.rd.service.UserService;
import ua.epam.rd.web.tools.AssessHelper;

import javax.servlet.http.HttpSession;

@Controller
public class RootController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET) // always set method
    public String rootPage(HttpSession session, Model model) {
        if (AssessHelper.notLoggedIn(session)) {
            return "authorization/login";
        }
        if (AssessHelper.isAdmin(session)) {
            return "admin/admin_home";
        } else {
            return "home";
        }
    }

    @RequestMapping("*")
    public String notFound() {
        return "not_found";
    }
}
