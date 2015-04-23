package ua.epam.rd.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RootController {
    @RequestMapping(value = "/", method = RequestMethod.GET) // always set method
//    @ResponseBody  //просто в поток вывода выводит (response), а не во вьz
        public String root(Model model) {
        model.addAttribute("msg","Hi!");
        return "hello";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET) // always set method
//    @ResponseBody  //просто в поток вывода выводит (response), а не во вьz
    public String login(Model model) {
        model.addAttribute("msg", "Hi!");
        return "login";
    }

    @RequestMapping(value = "/body", method = RequestMethod.GET) // always set method
    @ResponseBody  //просто в поток вывода выводит (response), а не во вьz
    public String body(Model model) {
        model.addAttribute("msg","Hi!");
        return "body";
    }
}
