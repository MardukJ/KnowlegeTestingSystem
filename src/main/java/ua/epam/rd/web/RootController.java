package ua.epam.rd.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.epam.rd.service.UserService;
import ua.epam.rd.web.tools.SecurityManager;

import javax.servlet.http.HttpSession;

@Controller
public class RootController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET) // always set method
    public String rootPage(HttpSession session, Model model) {
        if (SecurityManager.notLoggedIn(session)) {
            return "redirect:/login";
        }
        if (SecurityManager.isAdmin(session)) {
            return "redirect:admin/home";
        } else {
            return "redirect:home";
        }
    }

    @RequestMapping("*")
    public String defaultPage() {
        return "redirect:/not_found";
    }

    @RequestMapping("/not_found")
    public String notFound() {
        return "not_found";
    }
}
