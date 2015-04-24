package ua.epam.rd.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.epam.rd.domain.User;
import ua.epam.rd.service.UserService;
import ua.epam.rd.web.tools.Benchmark;

import java.util.List;

@Controller
public class RootController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET) // always set method
//    @ResponseBody  //просто в поток вывода выводит (response), а не во вьz
        public String root(Model model) {
        Benchmark bm = new Benchmark();
        bm.start();

        model.addAttribute("msg","Hi!");

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return "hello";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET) // always set method
//    @ResponseBody  //просто в поток вывода выводит (response), а не во вьz
    public String login(Model model) {
        Benchmark bm = new Benchmark();
        bm.start();

        model.addAttribute("msg", "Hi!");

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return "login";
    }

    @RequestMapping(value = "/body", method = RequestMethod.GET) // always set method
    @ResponseBody  //просто в поток вывода выводит (response), а не во вьz
    public String body(Model model) {
        Benchmark bm = new Benchmark();
        bm.start();

        model.addAttribute("msg","Hi!");


        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return "body";
    }


    @RequestMapping(value = "/all_users") // always set method
    public String getall(Model model) {
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
        return "users_list";
    }

    @RequestMapping(value = "/all_users_page") // always set method
//    @ResponseBody  //просто в поток вывода выводит (response), а не во вьz
    public String getpage(@RequestParam(defaultValue = "1") String page, Model model) {
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
        for (User u : userService.getAllFromPage(pageNum)) {
            msg += "ID = " + u.getId() + " mail = " + u.getEmail() + " hash " + u.getPassword() + " isBlocked?: " + u.getBlocked() + "<br>\n";
        }
        long totalPages = userService.getAllTotalPages();
        msg += "page " + pageNum + " of " + totalPages + "<br>\n";
        model.addAttribute("msg", msg);

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return "users_list";
    }
}
