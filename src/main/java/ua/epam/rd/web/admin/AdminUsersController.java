package ua.epam.rd.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.epam.rd.domain.Group;
import ua.epam.rd.domain.User;
import ua.epam.rd.service.GroupService;
import ua.epam.rd.service.UserService;
import ua.epam.rd.web.tools.*;
import ua.epam.rd.web.tools.SecurityManager;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 30.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
@Controller
public class AdminUsersController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/admin/all_users") // always set method
    public String getpage(@RequestParam(defaultValue = "1") String page,
                          @RequestParam(defaultValue = "both") String blocked,
                          @RequestParam(defaultValue = "both") String role,
                          @RequestParam(defaultValue = "no") String sort,
                          @RequestParam(defaultValue = "") String expression,
                          HttpSession session, Model model) {
        if (!ua.epam.rd.web.tools.SecurityManager.isAdmin(session)) return "redirect:/*";

        Benchmark bm = new Benchmark();
        bm.start();

        //Param convention for service layer & model
        Boolean blockedParam = null;
        if ("blocked".equals(blocked)) {
            blockedParam = Boolean.TRUE;
            model.addAttribute("blockedBlocked", "checked");
        } else if ("active".equals(blocked)) {
            blockedParam = Boolean.FALSE;
            model.addAttribute("blockedActive", "checked");
        } else {
            model.addAttribute("blockedBoth", "checked");
        }

        Boolean roleParam = null;
        if ("teacher".equals(role)) {
            roleParam = Boolean.TRUE;
            model.addAttribute("roleTeacher", "checked");
        } else if ("student".equals(role)) {
            roleParam = Boolean.FALSE;
            model.addAttribute("roleStudent", "checked");
        } else {
            model.addAttribute("roleBoth", "checked");
        }

        Boolean sortParam = null;
        if ("increase".equals(sort)) {
            sortParam = Boolean.TRUE;
            model.addAttribute("sortIncrease", "checked");
        } else if ("decrease".equals(sort)) {
            sortParam = Boolean.FALSE;
            model.addAttribute("sortDecrease", "checked");
        } else {
            model.addAttribute("sortNo", "checked");
        }

        //Security reasons expression filter
        String expressionParam = ExpressionFilter.loginFilter(expression);
        model.addAttribute("loginRegexp", expressionParam);

        String msg = new String();
        int pageNum;
        try {
            pageNum = Integer.parseInt(page);
            if (pageNum < 1) pageNum = 1;
        } catch (Exception e) {
            pageNum = 1;
        }
        int totalPages = userService.getAllTotalPagesWFiler(blockedParam, roleParam, expressionParam);
        if (pageNum > totalPages) pageNum = totalPages;

        List<User> users = userService.getAllFromPageWFilter(pageNum, blockedParam, roleParam, sortParam, expressionParam);
        model.addAttribute("userList", users);

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("firstPage", 1);

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return "admin/usersOp/users_list";
    }

    @RequestMapping(value = "/admin/user_details")
    @ExceptionHandler({IllegalArgumentException.class})
    public String userInfo(@RequestParam(defaultValue = "") String login, HttpSession session, Model model) {
        if (!ua.epam.rd.web.tools.SecurityManager.isAdmin(session)) return "redirect:/*";
        try {
            User user = userService.getUserInfo(login);
            model.addAttribute("login", user.getEmail());
            model.addAttribute("status", user.getBlocked() == Boolean.TRUE ? "Blocked" : "Active");
            model.addAttribute("groupsCounter", user.getMembership().size());

            return "/admin/usersOp/edit_user";
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            return "/admin/usersOp/find_user";
        }
    }

    @RequestMapping(value = "/admin/user_details", method = RequestMethod.POST, params = "block_action")
    @ExceptionHandler({IllegalArgumentException.class})
    public String userInfo(@RequestParam(defaultValue = "") String login, @RequestParam String block_action, HttpSession session, Model model) {
        if (!SecurityManager.isAdmin(session)) return "redirect:/*";
        try {
            //change blocked status
            if ("block".equals(block_action) || "unblock".equals(block_action)) {
                User user = userService.getUserInfo(login);
                if ("block".equals(block_action)) {
                    userService.blockUser(user.getId());
                } else if ("unblock".equals(block_action)) {
                    userService.unblockUser(user.getId());
                }
            }
            User user = userService.getUserInfo(login);
            model.addAttribute("login", user.getEmail());
            model.addAttribute("status", user.getBlocked() == Boolean.TRUE ? "Blocked" : "Active");
            return "/admin/usersOp/edit_user";
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            return "/admin/usersOp/find_user";
        }
    }

    //find user
    @RequestMapping(value = "/admin/find_user")
    public String findUserNoParam
    (HttpSession session, Model model) {
        if (!SecurityManager.isAdmin(session)) return "redirect:/*";
        return "/admin/usersOp/find_user";
    }

    @RequestMapping(value = "/admin/groups_of_user")
    @ExceptionHandler({IllegalArgumentException.class})
    public String groupsOfUser(@RequestParam(defaultValue = "") String login,
                              @RequestParam(defaultValue = "1") String page,
                              HttpSession session, Model model) {
        Benchmark bm = new Benchmark();
        bm.start();
        try {
            User user = userService.getUserInfo(login);
            List <Group> groupList = user.getMembership();
            model.addAttribute("groupList", groupList);
            model.addAttribute("login", user.getEmail());
        } catch (Exception e){
            model.addAttribute("msg",e.getMessage());
            return "/admin/userOp/users_list";
        }
        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return "/admin/usersOp/groups_of_user";
    }
}
