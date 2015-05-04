package ua.epam.rd.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.epam.rd.domain.Group;
import ua.epam.rd.domain.Question;
import ua.epam.rd.domain.QuestionAnswerOption;
import ua.epam.rd.domain.User;
import ua.epam.rd.service.GroupService;
import ua.epam.rd.service.QuestionService;
import ua.epam.rd.service.UserService;
import ua.epam.rd.web.tools.*;
import ua.epam.rd.web.tools.SecurityManager;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 03.05.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
@Controller
public class TeacherController {

    @Autowired
    UserService userService;
    @Autowired
    GroupService groupService;
    @Autowired
    QuestionService questionService;
    @Autowired
    ua.epam.rd.web.tools.SecurityManager securityManager;

    @RequestMapping(value = "/teacher/questions")
    public String home(HttpSession session, Model model) {
        if (ua.epam.rd.web.tools.SecurityManager.notLoggedIn(session)) return "redirect:/*";
        if (!securityManager.isTeacher(session)) return "redirect:/*";
        String view = new String();

        Benchmark bm = new Benchmark();
        bm.start();
        view="/teacher/groups";

        List <Group> membership = userService.getActiveGroupMembership(SecurityManager.getID(session));
        model.addAttribute("groupList", membership);

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return view;
    }

    @RequestMapping(value = "/teacher/group")
    public String group(@RequestParam(defaultValue = "") String name, HttpSession session, Model model) {
        if (ua.epam.rd.web.tools.SecurityManager.notLoggedIn(session)) return "redirect:/*";
        if (!securityManager.isTeacher(session)) return "redirect:/*";
        String view;

        Benchmark bm = new Benchmark();
        bm.start();

        if (securityManager.isUserHasAccessToGroup(name, session)) {
            view="/teacher/group_questions";
            List <Question> validQuestions = questionService.getAllValidByGroup(name);
            model.addAttribute("validQuestions", validQuestions);
            model.addAttribute("groupName", name);
        } else {
            view="redirect:/";
        }

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return view;
    }

    //неоптимальные обращения к базе - повторы
    @RequestMapping(value = "/teacher/delete_question", method = RequestMethod.POST)
    public String deleteQuestion(@RequestParam(defaultValue = "") Long idQuestion, HttpSession session, Model model) {
        if (ua.epam.rd.web.tools.SecurityManager.notLoggedIn(session)) return "redirect:/*";
        if (!securityManager.isTeacher(session)) return "redirect:/*";
        String view;

        Benchmark bm = new Benchmark();
        bm.start();
        try {
            if (securityManager.isUserHasAccessToQuestion(idQuestion, session)) {
                questionService.delete(idQuestion);
                view = "redirect:/teacher/group";
            } else {
                view = "redirect:/";
            }
        } catch (Exception e) {
            model.addAttribute("msg",e.getMessage());
            view = "redirect:/";
        }

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return view;
    }

    //review initial question
    @RequestMapping(value = "/teacher/edit_question", params = "!action")
    public String editQuestionViewInitial(@RequestParam(defaultValue = "") Long idQuestion, HttpSession session, Model model) {
        if (ua.epam.rd.web.tools.SecurityManager.notLoggedIn(session)) return "redirect:/*";
        if (!securityManager.isTeacher(session)) return "redirect:/*";
        String view;

        view="/teacher/edit_question";
        Benchmark bm = new Benchmark();
        bm.start();
        try {
            if (securityManager.isUserHasAccessToQuestion(idQuestion, session)) {
                Question question = questionService.getById(idQuestion);
                while (question.getOptions().size()<7) {
                    question.getOptions().add(new QuestionAnswerOption("",Boolean.FALSE));
                }
                model.addAttribute("question",question);
//                model.addAttribute("groupName",question.getGroupOfQuestion().getGroupName());
            } else {
                view = "redirect:/";
            }
        } catch (Exception e) {
            model.addAttribute("msg",e.getMessage());
            view = "redirect:/";
        }

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return view;
    }

    //@RequestParam(value="id", required=false)
    //preview or submit
    @RequestMapping(value = "/teacher/edit_question", params = "action")
    public String editQuestion(@ModelAttribute Question question,
                               //2do list?
                               @RequestParam (required = false) String optionText0, @RequestParam (required = false)  Boolean correctAnswer0,
                               @RequestParam (required = false) String optionText1, @RequestParam (required = false)  Boolean correctAnswer1,
                               @RequestParam (required = false) String optionText2, @RequestParam (required = false)  Boolean correctAnswer2,
                               @RequestParam (required = false) String optionText3, @RequestParam (required = false)  Boolean correctAnswer3,
                               @RequestParam (required = false) String optionText4, @RequestParam (required = false)  Boolean correctAnswer4,
                               @RequestParam (required = false) String optionText5, @RequestParam (required = false)  Boolean correctAnswer5,
                               @RequestParam (required = false) String optionText6, @RequestParam (required = false)  Boolean correctAnswer6,
                               @RequestParam (required = false) String groupName,
                               @RequestParam (required = true) String action,
                               HttpSession session, Model model) {
        if (ua.epam.rd.web.tools.SecurityManager.notLoggedIn(session)) return "redirect:/*";
        if (!securityManager.isTeacher(session)) return "redirect:/*";
        String view;

        view="/teacher/edit_question";
        Benchmark bm = new Benchmark();
        bm.start();
        //adding questions answers options
        //chinese code
        if ((optionText0!=null) && (optionText0.length()>0) && (correctAnswer0!=null)) {
            question.getOptions().add(new QuestionAnswerOption(optionText0,correctAnswer0));
            System.out.println(0);
        }

        if ((optionText1!=null) && (optionText1.length()>0) && (correctAnswer1!=null)) {
            question.getOptions().add(new QuestionAnswerOption(optionText1, correctAnswer1));
            System.out.println(1);
        }

        if ((optionText2!=null) && (optionText2.length()>0) && (correctAnswer2!=null)) {
            question.getOptions().add(new QuestionAnswerOption(optionText2,correctAnswer2));
            System.out.println(2);
        }

        if ((optionText3!=null) && (optionText3.length()>0) && (correctAnswer3!=null)) {
            question.getOptions().add(new QuestionAnswerOption(optionText3,correctAnswer3));
            System.out.println(3);
        }

        if ((optionText4!=null) && (optionText4.length()>0) && (correctAnswer4!=null)) {
            question.getOptions().add(new QuestionAnswerOption(optionText4,correctAnswer4));
            System.out.println(4);
        }

        if ((optionText5!=null) && (optionText5.length()>0) && (correctAnswer5!=null)) {
            question.getOptions().add(new QuestionAnswerOption(optionText5,correctAnswer5));
            System.out.println(5);
        }

        if ((optionText6!=null) && (optionText6.length()>0) && (correctAnswer6!=null)) {
            question.getOptions().add(new QuestionAnswerOption(optionText6,correctAnswer6));
            System.out.println(6);
        }

        while (question.getOptions().size()<7) {
            question.getOptions().add(new QuestionAnswerOption("",Boolean.FALSE));
        }

        try {
            if (((groupName==null) && (securityManager.isUserHasAccessToQuestion(question.getId(), session))) ||
                    (securityManager.isUserHasAccessToGroup(groupName, session)))
            {
                if (("preview".equals(action)) || (question.verifyMe()!=null)) {
                    String verifyMsg=question.verifyMe();
                    if (verifyMsg!=null) {
                        model.addAttribute("msg", verifyMsg);
                    }
                    model.addAttribute("question", question);
                    model.addAttribute("groupName", groupName);
                } else if ("submit".equals(action)) {
                    if (groupName==null) {
                        Question newVersion = questionService.updateVersion(question);
                        model.addAttribute("msg", "Question updated");
                        model.addAttribute("question", newVersion);
                    } else {
                        Question newQuestion = questionService.saveNewQuestion(question, groupName);
                        model.addAttribute("msg", "Question created");
                        model.addAttribute("question", newQuestion);
                        model.addAttribute("groupName", null);
                    }

                } else throw new RuntimeException();
            } else {
                view = "redirect:/";
            }
        } catch (Exception e) {
            model.addAttribute("msg",e.getMessage());
            view = "redirect:/";
        }

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return view;
    }

    //new question from blank
    @RequestMapping(value = "/teacher/new_question", params = "name")
    public String editQuestionViewInitial(String name, HttpSession session, Model model) {
        if (ua.epam.rd.web.tools.SecurityManager.notLoggedIn(session)) return "redirect:/*";
        if (!securityManager.isTeacher(session)) return "redirect:/*";
        String view;

        view="/teacher/edit_question";
        Benchmark bm = new Benchmark();
        bm.start();
        try {
            if (securityManager.isUserHasAccessToGroup(name, session)) {
                Question question = new Question();
                question.setBody("");
                question.setTeacherComment("");
                question.setReviewComment("");
                question.setGroupOfQuestion(groupService.getGroupInfo(name));
                while (question.getOptions().size()<7) {
                    question.getOptions().add(new QuestionAnswerOption("",Boolean.FALSE));
                }
                model.addAttribute("question",question);
                model.addAttribute("groupName",name);
            } else {
                view = "redirect:/";
            }
        } catch (Exception e) {
            model.addAttribute("msg",e.getMessage());
            view = "redirect:/";
        }

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return view;
    }
}
