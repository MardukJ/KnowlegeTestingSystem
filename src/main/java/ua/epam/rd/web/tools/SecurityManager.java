package ua.epam.rd.web.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ua.epam.rd.domain.Group;
import ua.epam.rd.domain.User;
import ua.epam.rd.service.GroupService;
import ua.epam.rd.service.QuestionService;
import ua.epam.rd.service.UserService;

import javax.servlet.http.HttpSession;

/**
 * Created by Mykhaylo Gnylorybov on 25.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
@Component
public class SecurityManager {

    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private QuestionService questionService;

    public static boolean notLoggedIn(HttpSession session) {
        if (session.getAttribute("SecurityManager.login") == null) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isAdmin(HttpSession session) {
        if ("true".equals(session.getAttribute("SecurityManager.admin"))) {
            return true;
        } else {
            return false;
        }
    }

    public static void setUserAttributesToSession(HttpSession session, User user) {
        session.setAttribute("SecurityManager.login", user.getEmail());
        if ("admin".equals(user.getEmail())) {
            session.setAttribute("SecurityManager.admin", "true");
        }
        session.setAttribute("SecurityManager.ID", user.getId());
    }

    public static void logout(HttpSession session) {
        session.invalidate();
    }

    public static String getLogin(HttpSession session) {
        return (String) session.getAttribute("SecurityManager.login");
    }

    public static Long getID(HttpSession session) {
        return (Long) session.getAttribute("SecurityManager.ID");
    }

    public boolean isTeacher(HttpSession session) {
        Long id = getID(session);
        return userService.isTeacher(id);
    }

    //if group blocked -> return false
    public boolean isUserHasAccessToGroup(String groupName, HttpSession session) {
        boolean accessGranted = false;
        try {
            Group group = groupService.getGroupInfo(groupName);
            //superuser
            if (isAdmin(session)) return true;
            //blocked group
            Long ID = getID(session);
            if (group.getBlocked().equals(Boolean.TRUE)) {
                return false;
            }
            //user access rights
            for (User u: group.getMembers()) {
                if ((u.getId().equals(ID))) {
                    accessGranted =true;
                    break;
                }
            }
        } catch (Exception e) {
            //group not found
            return false;
        }
        return accessGranted;
    }

    public boolean isUserHasAccessToQuestion(Long idQuestion, HttpSession session) {
        if (idQuestion==null) return false;
        //get groupName
        return isUserHasAccessToGroup(questionService.getGroupNameForQuestion(idQuestion),session);
    }
}
