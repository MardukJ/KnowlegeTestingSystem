package ua.epam.rd.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.epam.rd.domain.Group;
import ua.epam.rd.domain.User;
import ua.epam.rd.repository.GroupRepository;
import ua.epam.rd.repository.UserRepository;
import ua.epam.rd.service.GroupService;
import ua.epam.rd.service.mail.MailService;
import ua.epam.rd.web.tools.Benchmark;

/**
 * Created by Mykhaylo Gnylorybov on 25.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */

@Controller
public class DevController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupService groupService;

    //dev menu
    @RequestMapping(value = "/dev")
    @ResponseBody
    public String body(Model model) {
        String menu = "<a href=/dev/user_gen> add 100_000 users </a> <BR>";
        menu += "<a href=/dev/mail> Mail history </a> <BR>";
        menu += "<a href=/dev/welcome> Locale test </a> <BR>";
        menu += "<a href=/dev/group_gen> add 1_000 groups </a> <BR>";
        return menu;
    }

    @RequestMapping(value = "/dev/user_gen")
    @ResponseBody
    public String zasriUserami(Model model) {
        Benchmark bm = new Benchmark();
        bm.start();

        User user;

        //admin+tester
        if (userRepository.getTotalEntry() == 0) {
            user = new User();
            user.setEmail("admin");
            user.setPassword("12345");
            user.setBlocked(Boolean.FALSE);
            userRepository.add(user);
            user = new User();
            user.setEmail("tester");
            user.setPassword("12345");
            user.setBlocked(Boolean.FALSE);
            userRepository.add(user);
        }

        //blank users
        for (int i = 0; i < 100000; i++) {
            user = new User();
            user.setEmail("u" + i + "@mail.com");
            user.setPassword("12345");
            user.setBlocked(Boolean.TRUE);
            userRepository.add(user);
        }

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return String.valueOf(bm.getDifferce());
    }

    @RequestMapping(value = "/dev/group_gen")
    @ResponseBody
    public String zasriGroup(Model model) {
        Benchmark bm = new Benchmark();
        bm.start();

        Group group;

        //blank users
        for (int i = 0; i < 1000; i++) {
            group = new Group();
            group.setGroupName("BRED." + i);
            groupRepository.add(group);
            groupService.addUserToGroup("tester", "BRED." + i);
        }

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return String.valueOf(bm.getDifferce());
    }

    @RequestMapping(value = "/dev/mail")
    @ResponseBody
    public String mail(Model model) {
        String result = "";
        for (String s : mailService.getHistory()) {
            result += s + "<BR>";
        }
        return result;
    }

    @RequestMapping(value = "/dev/welcome")
    protected String Welcome() {
        return "dev/welcome";
    }
}
