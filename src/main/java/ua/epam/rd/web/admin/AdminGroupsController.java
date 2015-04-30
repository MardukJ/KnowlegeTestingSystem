package ua.epam.rd.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
public class AdminGroupsController {
    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "/admin/group_list")
    public String fullGroupList(@RequestParam(defaultValue = "1") String page,
                                @RequestParam(defaultValue = "both") String blocked,
                                @RequestParam(defaultValue = "no") String sort,
                                @RequestParam(defaultValue = "") String expression,
                                HttpSession session, Model model) {
        if (!SecurityManager.isAdmin(session)) return "redirect:/*";

        Benchmark bm = new Benchmark();
        bm.start();

        int pageNum;
        try {
            pageNum = Integer.parseInt(page);
            if (pageNum < 1) pageNum = 1;
        } catch (Exception e) {
            pageNum = 1;
        }

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
        model.addAttribute("groupRegexp", expressionParam);

        int totalPages = groupService.getAllTotalPagesWFiler(blockedParam,expressionParam);
        if (pageNum > totalPages) pageNum = totalPages;

        List<Group> groups = groupService.getAllFromPageWFilter(pageNum,blockedParam,sortParam,expressionParam);
        model.addAttribute("groupList", groups);

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("firstPage", 1);

        bm.stop();

        model.addAttribute("creationTime", bm.getDifferce());
        return "/admin/groupOp/group_list";
    }

    @RequestMapping(value = "/admin/group_details", params = "!block_action")
    @ExceptionHandler({IllegalArgumentException.class})
    public String groupInfo(@RequestParam(defaultValue = "") String name, HttpSession session, Model model) {
        if (!SecurityManager.isAdmin(session)) return "redirect:/*";
        try {
            Group group = groupService.getGroupInfo(name);
            model.addAttribute("name", group.getGroupName());
            model.addAttribute("status", group.getBlocked() == Boolean.TRUE ? "Blocked" : "Active");
            model.addAttribute("membersCount", group.getMembers().size());
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            return "/admin/groupOp/find_group";
        }
        return "/admin/groupOp/edit_group";
    }

    @RequestMapping(value = "/admin/group_details", method = RequestMethod.POST, params = "block_action")
    @ExceptionHandler({IllegalArgumentException.class})
    public String groupBlockAction(@RequestParam(defaultValue = "") String name, @RequestParam String block_action, HttpSession session, Model model) {
        if (!SecurityManager.isAdmin(session)) return "redirect:/*";
        try {
            //change blocked status
            if ("block".equals(block_action) || "unblock".equals(block_action)) {
                Group group = groupService.getGroupInfo(name);
                if ("block".equals(block_action)) {
                    groupService.blockGroup(group.getId());
                } else if ("unblock".equals(block_action)) {
                    groupService.unblockGroup(group.getId());
                }
            }

            Group group = groupService.getGroupInfo(name);
            model.addAttribute("name", group.getGroupName());
            model.addAttribute("status", group.getBlocked() == Boolean.TRUE ? "Blocked" : "Active");
            model.addAttribute("membersCount", group.getMembers().size());
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            return "/admin/groupOp/find_group";
        }
        return "/admin/groupOp/edit_group";
    }

    @RequestMapping(value = "/admin/create_group", params = "!name")
    @ExceptionHandler({IllegalArgumentException.class})
    public String createGroupView(HttpSession session, Model model) {
        if (!SecurityManager.isAdmin(session)) return "redirect:/*";
        return "/admin/groupOp/create_group";
    }

    @RequestMapping(value = "/admin/create_group", params = "groupName", method = RequestMethod.POST)
    @ExceptionHandler({IllegalArgumentException.class})
    public String createGroupAction(@ModelAttribute Group group, HttpSession session, Model model) {
        if (!SecurityManager.isAdmin(session)) return "redirect:/*";
        try {
            groupService.registerNew(group);
            model.addAttribute("msg", "Group registered successfully");
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            if ((group != null) && (group.getGroupName() != null)) {
                model.addAttribute("name", group.getGroupName());
            }
            return "/admin/groupOp/create_group";
        }
        return "redirect:/admin/group_details?name=" + group.getGroupName();
    }

    @RequestMapping(value = "/admin/find_group", params = "!name")
    @ExceptionHandler({IllegalArgumentException.class})
    public String findGroupView(HttpSession session, Model model) {
        if (!SecurityManager.isAdmin(session)) return "redirect:/*";
        return "/admin/groupOp/find_group";
    }

    @RequestMapping(value = "/admin/find_group", params = "name")
    @ExceptionHandler({IllegalArgumentException.class})
    public String findGroupAction(@RequestParam String name, HttpSession session, Model model) {
        if (!SecurityManager.isAdmin(session)) return "redirect:/*";
        try {
            Group group = groupService.getGroupInfo(name);
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("name", name);
            return "/admin/groupOp/find_group";
        }
        return "redirect:/admin/group_details?name=" + name;
    }

    @RequestMapping(value = "/admin/users_of_group")
    @ExceptionHandler({IllegalArgumentException.class})
    public String userOfGroup(@RequestParam(defaultValue = "") String name,
                              @RequestParam(defaultValue = "1") String page,
                              HttpSession session, Model model) {
        try {
            Group group = groupService.getGroupInfo(name);
            List <User> userList = group.getMembers();
            model.addAttribute("userList", userList);
            model.addAttribute("name", group.getGroupName());
        } catch (Exception e){
            model.addAttribute("msg",e.getMessage());
            return "/admin/groupOp/group_list";
        }
        return "/admin/groupOp/users_of_group";
    }

    @RequestMapping(value = "/admin/add_to_group", method = RequestMethod.GET)
    @ExceptionHandler({IllegalArgumentException.class})
    public String addToGroup(@RequestParam(defaultValue = "") String name,
                              HttpSession session, Model model) {
//        try {
//            Group group = groupService.getGroupInfo(name);
//            List <User> userList = group.getMembers();
//            model.addAttribute("userList", userList);
//            model.addAttribute("name", group.getGroupName());
//        } catch (Exception e){
//            model.addAttribute("msg",e.getMessage());
//            return "/admin/groupOp/group_list";
//        }
        return "/admin/groupOp/invite_to_group";
    }
}
