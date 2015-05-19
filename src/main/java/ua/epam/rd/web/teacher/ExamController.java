package ua.epam.rd.web.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.epam.rd.domain.*;
import ua.epam.rd.service.ExamService;
import ua.epam.rd.service.QuestionService;
import ua.epam.rd.service.UserService;
import ua.epam.rd.web.tools.*;
import ua.epam.rd.web.tools.SecurityManager;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 06.05.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */

@Controller
public class ExamController {
    @Autowired
    ua.epam.rd.web.tools.SecurityManager securityManager;

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @Autowired
    ExamService examService;

    //exams list + create button
    @RequestMapping(value = "/teacher/exams")
    public String teacherMenu(HttpSession session, Model model) {
        if (ua.epam.rd.web.tools.SecurityManager.notLoggedIn(session)) return "redirect:/*";
        if (!securityManager.isTeacher(session)) return "redirect:/*";
        String view = "/teacher/exam_menu";

        Benchmark bm = new Benchmark();
        bm.start();

        List <Exam> exams = examService.getExamsOfUser(SecurityManager.getID(session));
        model.addAttribute("examList", exams);

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return view;
    }

    @RequestMapping(value = "/teacher/create_exam")
    @ExceptionHandler({IllegalArgumentException.class})
    public String createExamQuestions(@RequestParam(defaultValue = "1") String pageA,
                                      @RequestParam(defaultValue = "1") String pageE,
                                      @RequestParam(required = false) String idQuestion,
                                      @RequestParam(required = false) String action,
                                      HttpSession session, Model model) {
        if (ua.epam.rd.web.tools.SecurityManager.notLoggedIn(session)) return "redirect:/*";
        if (!securityManager.isTeacher(session)) return "redirect:/*";
        String view = "/teacher/exam_create_questions";

        Benchmark bm = new Benchmark();
        bm.start();
        //try to action
        Exam myExam = securityManager.getExamBuffer(session);
        if (myExam == null) {
            myExam = new Exam();
            myExam.setCreator(userService.getUserInfo(SecurityManager.getLogin(session)));
        }
        Long idQuestionParam;
        try {
            idQuestionParam = Long.valueOf(idQuestion);
        } catch (Exception e) {
            idQuestionParam = null;
        }
        if (("addQ".equals(action)) && (idQuestionParam != null)) {
            //try to add question to test
            Question question = questionService.getById(idQuestionParam);
            if (question != null) {
                boolean contains = false;
                Iterator<Question> it = myExam.getQuestions().iterator();
                while (it.hasNext()) {
                    Question q = it.next();
                    if (q.getId().equals(idQuestionParam)) {
                        contains = true;
                        break;
                    }
                }
                if (!contains) {
                    myExam.getQuestions().add(question);
                }
            }
        } else if (("removeQ".equals(action)) && (idQuestionParam != null)) {
            //try to remove question from test
            Iterator<Question> it = myExam.getQuestions().iterator();
            while (it.hasNext()) {
                Question q = it.next();
                if (q.getId().equals(idQuestionParam)) {
                    it.remove();
                    break;
                }
            }
        } else if ("reset".equals(action)) {
            myExam = new Exam();
        }
        securityManager.setExamBuffer(myExam, session);

        //Exam questions w pagination
        //2DO: refactor
        final int PAGE_SIZE_BY_USER = 2;
        int totalPagesE = myExam.getQuestions().size();
        totalPagesE = (int) (totalPagesE / PAGE_SIZE_BY_USER + (totalPagesE % PAGE_SIZE_BY_USER == 0 ? 0 : 1));
        if (totalPagesE == 0) totalPagesE = 1;
        List<Question> examQuestionsPage = new LinkedList<Question>();

        int pageExam;
        try {
            pageExam = Integer.valueOf(pageE);
            if (pageExam < 1) {
                pageExam = 1;
            } else if (pageExam > totalPagesE) {
                pageExam = totalPagesE;
            }
        } catch (NumberFormatException e) {
            pageExam = 1;
        }

        int firstIndex = (pageExam - 1) * PAGE_SIZE_BY_USER;
        int lastIndex = firstIndex + PAGE_SIZE_BY_USER;
        for (int i = firstIndex; (i < lastIndex) && (i < myExam.getQuestions().size()); i++) {
            examQuestionsPage.add(myExam.getQuestions().get(i));
        }

        model.addAttribute("examQuestions", examQuestionsPage);
        model.addAttribute("examQuestionsSize", myExam.getQuestions().size());

        model.addAttribute("currentPageE", pageExam);
        model.addAttribute("totalPagesE", totalPagesE);
        model.addAttribute("firstPageE", 1);

        // Available questions w pagination
        Long userId = SecurityManager.getID(session);
        int totalPagesA = questionService.getAllActiveByUserTotalPages(userId);
        int pageAvailable;
        try {
            pageAvailable = Integer.valueOf(pageA);
            if (pageAvailable < 1) {
                pageAvailable = 1;
            } else if (pageAvailable > totalPagesA) {
                pageAvailable = totalPagesA;
            }
        } catch (NumberFormatException e) {
            pageAvailable = 1;
        }

        model.addAttribute("currentPageA", pageAvailable);
        model.addAttribute("totalPagesA", totalPagesA);
        model.addAttribute("firstPageA", 1);

        List<Question> allAvailable = questionService.getAllActiveByUserFromPage(pageAvailable, userId);
        model.addAttribute("availableQuestions", allAvailable);
        // ---

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return view;
    }


    @RequestMapping(value = "/teacher/create_exam_user")
    @ExceptionHandler({IllegalArgumentException.class})
    public String createExamUsers(@RequestParam(defaultValue = "1") String pageA,

                                  @RequestParam(defaultValue = "1") String pageE,
                                  @RequestParam(defaultValue = "no") String sort,
                                  @RequestParam(defaultValue = "") String expression,

                                  @RequestParam(required = false) String userMail,
                                  @RequestParam(required = false) String action,
                                  HttpSession session, Model model) {

        Benchmark bm = new Benchmark();
        bm.start();

        String view = "/teacher/exam_create_questions2";
        String view_back_to_step_1 = "redirect:/teacher/create_exam";

        //Security
        if (ua.epam.rd.web.tools.SecurityManager.notLoggedIn(session)) return "redirect:/*";
        if (!securityManager.isTeacher(session)) return "redirect:/*";
        Exam myExam = securityManager.getExamBuffer(session);

        //Still no questions added
        if ((myExam == null) || (myExam.getQuestions().size() == 0)) {
            return view_back_to_step_1;
        }


        //All users list
        //Param convention for service layer & model
        Boolean blockedParam = Boolean.FALSE;
        Boolean roleParam = null;
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
        String expressionParam = ExpressionFilter.loginFilter(expression);
        model.addAttribute("loginRegexp", expressionParam);

        int pageNumE;
        try {
            pageNumE = Integer.parseInt(pageE);
            if (pageNumE < 1) pageNumE = 1;
        } catch (Exception e) {
            pageNumE = 1;
        }
        int totalPagesE = userService.getAllTotalPagesWFiler(blockedParam, roleParam, expressionParam);
        if (pageNumE > totalPagesE) pageNumE = totalPagesE;
        model.addAttribute("firstPageE", 1);
        model.addAttribute("currentPageE", pageNumE);
        model.addAttribute("totalPagesE", totalPagesE);


        List<User> users = userService.getAllFromPageWFilter(pageNumE, blockedParam, roleParam, sortParam, expressionParam);
        model.addAttribute("userListE", users);

        //Added users list
        model.addAttribute("userListA", myExam.getRecievers());


        //Actions here
        if (("addU".equals(action)) && (userMail != null)) {
            //try to add question to test
            User user = userService.getUserInfo(userMail);
            if (user != null) {
                Long idNewUser = user.getId();
                Iterator<User> it = myExam.getRecievers().iterator();
                boolean alreadyAdded = false;
                while (it.hasNext()) {
                    User u = it.next();
                    if (u.getId().equals(idNewUser)) {
                        alreadyAdded = true;
                        break;
                    }
                }
                if (!alreadyAdded) {
                    myExam.getRecievers().add(user);
                    model.addAttribute("msg", "user has been successfully added");
                } else {
                    model.addAttribute("msg", "user already added");
                }
            }
        } else if (("removeU".equals(action)) && (userMail != null)) {
            Iterator<User> it = myExam.getRecievers().iterator();
            boolean needToRemove = false;
            while (it.hasNext()) {
                User u = it.next();
                if (u.getEmail().equals(userMail)) {
                    needToRemove = true;
                    it.remove();
                    model.addAttribute("msg", "user has been successfully removed");
                    break;
                }
            }
            if (!needToRemove) {
                model.addAttribute("msg", "user already removed");
            }
        } else if (("newU".equals(action)) && (userMail != null)) {
            //register new user
            userMail=userMail.toLowerCase();
            User user;
            try {
                user = userService.getUserInfo(userMail);
            } catch (Exception e) {
                user = new User();
                user.setEmail(userMail);
            }
            if (user.getId()!=null) {
                Long idNewUser = user.getId();
                Iterator<User> it = myExam.getRecievers().iterator();
                boolean alreadyAdded = false;
                while (it.hasNext()) {
                    User u = it.next();
                    if (u.getId().equals(idNewUser)) {
                        alreadyAdded = true;
                        break;
                    }
                }
                if (!alreadyAdded) {
                    myExam.getRecievers().add(user);
                    model.addAttribute("msg", "user has been successfully added");
                } else {
                    model.addAttribute("msg", "user already added");
                }
            } else {
                if (user.verifyMail() != null) {
                    model.addAttribute("msg", "incorrect email");
                } else {
                    userService.registerNew(user);
                    model.addAttribute("msg", "user has been successfully registered");
                    myExam.getRecievers().add(user);
                }
            }
        }

        //Save changes
        securityManager.setExamBuffer(myExam, session);

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return view;
    }

    @RequestMapping(value = "/teacher/create_exam_final")
    @ExceptionHandler({IllegalArgumentException.class})
    public String createExamUsers(@RequestParam(required = false) String action,

                                  @RequestParam(required = false) String nameParam,
                                  @RequestParam(required = false) String scoringAlgorithmParam,
                                  @RequestParam(required = false) String startWindowOpenParamD,
                                  @RequestParam(required = false) String startWindowOpenParamT,
                                  @RequestParam(required = false) String maxLateTimeInMinutesParam,
                                  @RequestParam(required = false) String testTimeInMinutesParam,
                                  @RequestParam(required = false) String showResultsParam,
                                  HttpSession session, Model model) {

        final int MAX_LATE_TIME_CAP = 1440;
        final int TEST_TIME_IN_MINUTES_CAP = 1440;

        Benchmark bm = new Benchmark();
        bm.start();

        String view = "/teacher/exam_create_questions3";
        String view_back_to_step_1 = "redirect:/teacher/create_exam";
        String view_back_to_step_2 = "redirect:/teacher/create_exam_user";

        //Security
        if (ua.epam.rd.web.tools.SecurityManager.notLoggedIn(session)) return "redirect:/*";
        if (!securityManager.isTeacher(session)) return "redirect:/*";
        Exam myExam = securityManager.getExamBuffer(session);

        //Still no questions added
        if ((myExam == null) || (myExam.getQuestions().size() == 0)) {
            return view_back_to_step_1;
        }
        //Still no users added
        if ((myExam == null) || (myExam.getRecievers().size() == 0)) {
            return view_back_to_step_2;
        }

        //Form reading
        //nameParam
        if (nameParam!=null) myExam.setName(nameParam);

        //scoringAlgorithmParam
        try {
            myExam.setScoringAlgorithm(ScoringAlgorithm.valueOf(scoringAlgorithmParam));
        }
        catch (Exception e) {
            //ignore
        }

        //maxLateTimeInMinutesParam
        try {
            int maxLateTimeInMinutes = Integer.parseInt(maxLateTimeInMinutesParam);
            if (maxLateTimeInMinutes<0) {
                maxLateTimeInMinutes=0;
            } else if (maxLateTimeInMinutes>MAX_LATE_TIME_CAP){
                maxLateTimeInMinutes=MAX_LATE_TIME_CAP;
            }
            myExam.setMaxLateTimeInMinutes(maxLateTimeInMinutes);
        }
        catch (Exception e) {
            //ignore
        }

        //testTimeInMinutesParam
        try {
            int testTimeInMinutes = Integer.parseInt(testTimeInMinutesParam);
            if (testTimeInMinutes<1) {
                testTimeInMinutes=1;
            } else if (testTimeInMinutes>TEST_TIME_IN_MINUTES_CAP){
                testTimeInMinutes=TEST_TIME_IN_MINUTES_CAP;
            }
            myExam.setTestTimeInMinutes(testTimeInMinutes);
        }
        catch (Exception e) {
            //ignore
        }

        //showResultsParam
        try {
            Boolean showResults = Boolean.valueOf(showResultsParam);
            myExam.setShowResults(showResults);
        }
        catch (Exception e) {
            //ignore
        }

        //date&time
        try {
            //        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date dateParam = inFormat.parse(startWindowOpenParamD + "T" + startWindowOpenParamT);
            myExam.setStartWindowOpen(dateParam);
        }
        catch (Exception e) {
            //ignore
        }

        //Save changes
        securityManager.setExamBuffer(myExam, session);

        //create exam action
        if ("create".equals(action)) {
            String verifyResults = myExam.verifyMe();
            if (verifyResults!=null) {
                model.addAttribute("msg", verifyResults);
            } else {
                //saving new exam
                examService.saveNewExam(securityManager.getExamBuffer(session));
                securityManager.setExamBuffer(null,session);
                return "redirect:/";
                //2do - redirect to new exam page
            }
        }

        //Form filling
        model.addAttribute("myExam", myExam);
        model.addAttribute("DD", String.format("%02d", myExam.getStartWindowOpen().getDate()));
        model.addAttribute("MONTH",String.format("%02d", myExam.getStartWindowOpen().getMonth() + 1));
        model.addAttribute("YYYY",String.format("%04d", myExam.getStartWindowOpen().getYear()+1900));

        model.addAttribute("HH", String.format("%02d", myExam.getStartWindowOpen().getHours()));
        model.addAttribute("MM", String.format("%02d", myExam.getStartWindowOpen().getMinutes()));


        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return view;
    }

    @RequestMapping(value = "/teacher/exam")
    public String examDetails(@RequestParam(required = true) String idExam,
                              HttpSession session, Model model) {
        if (ua.epam.rd.web.tools.SecurityManager.notLoggedIn(session)) return "redirect:/*";
        if (!securityManager.isTeacher(session)) return "redirect:/*";
        String examMenu = "redirect:/teacher/exams";
        String examInfo = "/teacher/exam_details";

        String view;
        Benchmark bm = new Benchmark();
        bm.start();

        Long idE = Long.valueOf(idExam);
        Exam exam=examService.getById(idE);
        System.out.println(exam.getInvites().size());
        System.out.println(exam.getInvites().get(0).getInviteReceiver().getEmail());

        //check access
        if (exam.getCreator().getId().equals(SecurityManager.getID(session))) {
            model.addAttribute("exam", exam);
            model.addAttribute("invites", exam.getInvites());

            view=examInfo;
        } else {
            return "redirect:/*";
        }

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return view;
    }
}
