package ua.epam.rd.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.epam.rd.domain.User;
import ua.epam.rd.service.UserService;
import ua.epam.rd.web.tools.AssessHelper;
import ua.epam.rd.web.tools.Benchmark;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 25.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/admin/home") // always set method
    public String adminHome(HttpSession session, Model model) {
        if (!AssessHelper.isAdmin(session)) return "redirect:*";

        Benchmark bm = new Benchmark();
        bm.start();

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return "admin/admin_home";
    }

    @RequestMapping(value = "/all_users") // always set method
    public String getall(HttpSession session, Model model) {
        if (!AssessHelper.isAdmin(session)) return "redirect:*";

        Benchmark bm = new Benchmark();
        bm.start();

        String msg = new String();
        List<User> users = userService.getAllNow();
        for (User u : users) {
            msg += "ID = " + u.getId() + " mail = " + u.getEmail() + " hash " + u.getPassword() + " isBlocked?: " + u.getBlocked() + "<br>\n";
        }
        model.addAttribute("msg", msg);

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return "admin/users_list";
    }

    @RequestMapping(value = "/all_users_page") // always set method
    public String getpage(@RequestParam(defaultValue = "1") String page, HttpSession session, Model model) {
        if (!AssessHelper.isAdmin(session)) return "redirect:*";

        Benchmark bm = new Benchmark();
        bm.start();

        String msg = new String();
        int pageNum;
        try {
            pageNum = Integer.parseInt(page);
            if (pageNum < 1) pageNum = 1;
        } catch (Exception e) {
            pageNum = 1;
        }
        int totalPages = userService.getAllTotalPages();
        if (pageNum > totalPages) pageNum = totalPages;

        for (User u : userService.getAllFromPage(pageNum)) {
            msg += "ID = " + u.getId() + " mail = " + u.getEmail() + " hash " + u.getPassword() + " isBlocked?: " + u.getBlocked() + "<br>\n";
        }
        msg += "page " + pageNum + " of " + totalPages + "<br>\n";
        model.addAttribute("msg", msg);

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return "admin/users_list";
    }
}
