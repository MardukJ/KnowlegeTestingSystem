package ua.epam.rd.web.tools;

import org.springframework.ui.Model;
import ua.epam.rd.domain.User;

import javax.servlet.http.HttpSession;

/**
 * Created by Mykhaylo Gnylorybov on 25.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
public class SecurityManager {

    public static boolean notLoggedIn(HttpSession session) {
        if (session.getAttribute("login") == null) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isAdmin(HttpSession session) {
        if ("true".equals(session.getAttribute("admin"))) {
            return true;
        } else {
            return false;
        }
    }

    public static void setUserAttributesToSession(HttpSession session, User user) {
        session.setAttribute("login", user.getEmail());
        if ("admin".equals(user.getEmail())) {
            session.setAttribute("admin", "true");
        }
    }

    public static void addUserAttributesToModel(HttpSession session, Model model) {
        model.addAttribute("login", session.getAttribute("login"));
        if (isAdmin(session)) {
            model.addAttribute("admin", "true");
        }
    }

    public static void logout(HttpSession session) {
        session.invalidate();
    }

    public static String getLogin(HttpSession session) {
        return (String) session.getAttribute("login");
    }
}
