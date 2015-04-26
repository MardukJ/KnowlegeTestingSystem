package ua.epam.rd.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.epam.rd.domain.User;
import ua.epam.rd.service.UserService;
import ua.epam.rd.web.tools.AssessHelper;
import ua.epam.rd.web.tools.Benchmark;

import javax.servlet.http.HttpSession;

/**
 * Created by Mykhaylo Gnylorybov on 25.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 * <p>
 * Login/logout/restore/etc.
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGet(HttpSession session, Model model) {
        Benchmark bm = new Benchmark();
        bm.start();

        if (AssessHelper.notLoggedIn(session)) {
            model.addAttribute("msg", "Please log in!");
        } else {
            return "redirect:/";
        }

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return "authorization/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ExceptionHandler({IllegalArgumentException.class})
    public String loginPost(@RequestParam(defaultValue = "") String login,
                            @RequestParam(defaultValue = "") String password,
                            HttpSession session,
                            Model model) {
        Benchmark bm = new Benchmark();
        bm.start();

        if (!AssessHelper.notLoggedIn(session)) {
            return "redirect:/";
        }

        try {
            User user = userService.checkPasswordAndGetUser(login, password);
            if (user.getBlocked())
                throw new IllegalStateException(user.getEmail() + " is blocked. Please contact your system administrator");
            //login successful
            model.addAttribute("msg", "Welcome " + user.getEmail() + "!");
            AssessHelper.setUserAttributesToSession(session, user);
            return "forward";
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
        }

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return "authorization/login";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session, Model model) {

        session.invalidate();

        return "redirect:/";
    }

    @RequestMapping(value = "/restore", method = RequestMethod.GET, params = "!token")
    public String restoreGet(HttpSession session, Model model) {
        Benchmark bm = new Benchmark();
        bm.start();

        if (!AssessHelper.notLoggedIn(session)) {
            return "redirect:/";
        }

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return "authorization/restore";
    }

    @RequestMapping(value = "/restore", method = RequestMethod.GET, params = "token")
    public String tryTokenGet(@RequestParam String token, HttpSession session, Model model) {
        Benchmark bm = new Benchmark();
        bm.start();

        User user = userService.validateRestorePasswordToken(token);
        if (user == null) {
            model.addAttribute("msg", "invalid token!");
        } else {
            model.addAttribute("msg", "new password has been sent by e-mail");
            AssessHelper.setUserAttributesToSession(session, user);
        }
        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return "forward";
    }

    @RequestMapping(value = "/restore", method = RequestMethod.POST)
    @ExceptionHandler({IllegalArgumentException.class})
    public String restorePost(@RequestParam(defaultValue = "") String login,
                              HttpSession session,
                              Model model) {
        Benchmark bm = new Benchmark();
        bm.start();

        if (!AssessHelper.notLoggedIn(session)) {
            return "redirect:/";
        }

        try {
            userService.sendRestorePasswordToken(login);
            //token created
            model.addAttribute("msg", "Password token was send by mail ");
            return "forward";
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
        }

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return "authorization/restore";
    }
}
