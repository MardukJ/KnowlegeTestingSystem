package ua.epam.rd.web.sudent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import ua.epam.rd.domain.*;
import ua.epam.rd.service.ExamService;
import ua.epam.rd.service.InviteService;
import ua.epam.rd.service.UserService;
import ua.epam.rd.web.tools.*;
import ua.epam.rd.web.tools.SecurityManager;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by Mykhaylo Gnylorybov on 11.05.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
@Controller
public class StudentController {
    @Autowired
    UserService userService;
    @Autowired
    ua.epam.rd.web.tools.SecurityManager securityManager;
    @Autowired
    ExamService examService;
    @Autowired
    InviteService inviteService;


    @RequestMapping ("/exams")
    public String examsList(HttpSession session, Model model) {
        Benchmark bm = new Benchmark();
        bm.start();

        if (ua.epam.rd.web.tools.SecurityManager.notLoggedIn(session)) return "redirect:/*";

        String view = "/user/exams";


        model.addAttribute("invitesList", userService.getInvitesByUser(SecurityManager.getID(session)));

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return view;
    }


    @RequestMapping (value = "/exam")
    public String examsList(@RequestParam(required = true) String inviteIdParam,
                            @RequestParam(required = false) String action,
                            @RequestParam(required = false) String index,
                            @RequestParam(required = false) String questionIdParam,
                            WebRequest webRequest,
                            HttpSession session, Model model) {
        String view ="/";
        String backToExamsList = "redirect:/exams";
        String examWait = "/user/exam_wait";
        String examStart = "/user/exam_start";
        String examResult = "/user/exam_result";
        String examAnswer = "/user/exam_answer";


        Benchmark bm = new Benchmark();
        bm.start();
        Long inviteId = Long.parseLong(inviteIdParam);
        Long questionIdL = null;
        if (questionIdParam!=null) {
            questionIdL = Long.valueOf(questionIdParam);
        }

        //not logged in
        if (ua.epam.rd.web.tools.SecurityManager.notLoggedIn(session)) return "redirect:/*";
        Invite invite = inviteService.getByIdWExamAndUser(inviteId);
        //no invite or no access
        if ((invite==null) || (!invite.getInviteReceiver().getId().equals(SecurityManager.getID(session)))) {
            return backToExamsList;
        }

        Long indexL = null;
        try {
            indexL = Long.valueOf(index);
            if (indexL<0) {
                indexL=new Long(0);
            } else if (indexL>=invite.getInviteExam().getQuestions().size()) {
                indexL=new Long(0);
            }
        } catch (Exception e) {
            indexL = new Long(0);
        }

        //action
        if ("do".equals(action)) {
            //simple exam page
            view = examAnswer;
            model.addAttribute("invite",invite);
            model.addAttribute("question",invite.getInviteExam().getQuestions().get(indexL.intValue()));
            model.addAttribute("questionIndex",indexL);
        } else if ("save".equals(action)) {
            //saving answers
            Map<String, String[]> params = webRequest.getParameterMap();
            Question question = invite.getInviteExam().getQuestions().get(indexL.intValue());
            for (QuestionAnswerOption o : question.getOptions()) {
                String search = o.getId().toString();
                boolean hasParam = false;
                String value [] =params.get("option");
                if (value!=null) {
                    for (String param : value) {
                        if ((param != null) && (param.equals(search))) {
                            hasParam = true;
                            break;
                        }
                    }
                }
                if (hasParam) {
                    invite.setAnswerForQuestionOption(o.getId(),Boolean.TRUE);
                } else {
                    invite.setAnswerForQuestionOption(o.getId(), Boolean.FALSE);
                }
            }
            inviteService.save(invite);

            view = examAnswer;
            model.addAttribute("invite",invite);
            model.addAttribute("question",invite.getInviteExam().getQuestions().get(indexL.intValue()));
            model.addAttribute("questionIndex",indexL);
        } else {
            //No action - just view result/wait for new/approve begin
            if ((invite.getInviteStatus().equals(InviteStatus.FINISHED)) || (invite.getInviteStatus().equals(InviteStatus.NO_SHOW))) {
                model.addAttribute("invite", invite);
                view = examResult;
            } else if (invite.getInviteStatus().equals(InviteStatus.NEW)) {
                long waitTimer = invite.getInviteExam().getStartWindowOpen().getTime() - System.currentTimeMillis();
                if (waitTimer > 0) {
                    //test not stated yet - show timer
                    view = examWait;
                    long timerHH = waitTimer / 3600000;
                    long timerMM = (waitTimer - timerHH * 3600000) / 60000;
                    long timerSS = waitTimer % 60000 / 1000;
                    model.addAttribute("timerHH", timerHH);
                    model.addAttribute("timerMM", timerMM);
                    model.addAttribute("timerSS", timerSS);
                    model.addAttribute("invite", invite);
                } else {
                    model.addAttribute("invite", invite);
                    view = examStart;
                }
                //СЮДА ПРОВЕРКУ НА ОПОЗДАНИЕ
            }
        }

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return view;
    }
}
