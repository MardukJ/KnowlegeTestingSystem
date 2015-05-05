package ua.epam.rd.web.teacher;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Mykhaylo Gnylorybov on 06.05.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */

@Controller
public class TestController {
    @RequestMapping(value = "/teacher/exams")
    public String teacherMenu() {
        return "/teacher/exam_menu";
    }
}
