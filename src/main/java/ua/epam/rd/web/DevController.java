package ua.epam.rd.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.epam.rd.domain.*;
import ua.epam.rd.repository.ExamRepository;
import ua.epam.rd.repository.GroupRepository;
import ua.epam.rd.repository.QuestionRepository;
import ua.epam.rd.repository.UserRepository;
import ua.epam.rd.service.ExamService;
import ua.epam.rd.service.GroupService;
import ua.epam.rd.service.QuestionService;
import ua.epam.rd.service.mail.MailService;
import ua.epam.rd.web.tools.Benchmark;

import java.util.Date;
import java.util.Random;

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

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ExamService examService;

    @Autowired
    private ExamRepository examRepository;


    //dev menu
    @RequestMapping(value = "/dev")
    @ResponseBody
    public String body(Model model) {
        String menu = "<a href=/dev/mail> <H1>Mail history</H1> </a> <BR>";
        menu += "<a href=/dev/evil_gen> <H1>EVIL GENERATOR</H1> </a> <BR>";
        menu += "<a href=/dev/welcome> <H1>Locale test</H1> </a> <BR>";
        menu += "<a href=/dev/happy_gen> <H1>HAPPINESS GENERATOR</H1> </a> <BR>";
        return menu;
    }

    @RequestMapping(value = "/dev/evil_gen")
    @ResponseBody
    public String zasriUserami(Model model) {
        System.out.println("EVIL GEN");
        int div = 500;
        final int EVIL_USERS = 50_000/div;
        final Boolean EVIL_USER_BLOCKED_CONDITION = Boolean.FALSE;

        final int EVIL_GROUPS = 1_000/div;

        final int EVIL_TEACHERS = 1_000/div;
        final int EVIL_GROUPS_PER_TEACHER = 5;

        final int TRASH_QUESTIONS_PER_GROUPS = 10;
        final int TRASH_QUESTIONS_OPTIONS = 4;

        final int TRASH_EXAMS = 1_000/div;


        Benchmark bm = new Benchmark();
        bm.start();

        User user;
        Group group;
        Question question;

        Random rnd = new Random(System.currentTimeMillis());

        //blank users
        for (int i = 0; i < EVIL_USERS; i++) {
            user = new User();
            user.setEmail("user" + String.format("%06d",i) + "@trash.com");
            System.out.println(user.getEmail());
            user.setPassword("12345");
            user.setBlocked(EVIL_USER_BLOCKED_CONDITION);
            userRepository.add(user);
        }

        //blank groups
        for (int i = 0; i < EVIL_GROUPS; i++) {
            group = new Group();
            group.setGroupName("Z.TRASH.GROUP." + String.format("%06d",i));
            groupRepository.add(group);
        }

        //blank teachers
        for (int i = 0; i < EVIL_TEACHERS; i++) {
            user = new User();
            user.setEmail("teacher" + String.format("%06d",i) + "@trash.com");
            System.out.println(user.getEmail());
            user.setPassword("12345");
            user.setBlocked(EVIL_USER_BLOCKED_CONDITION);

            userRepository.add(user);

            for (int j = 0;j<EVIL_GROUPS_PER_TEACHER;j++) {
                String groupName = "Z.TRASH.GROUP." + String.format("%06d",rnd.nextInt(EVIL_GROUPS));
                //already a group member
                try {
                    groupService.addUserToGroup(user.getEmail(), groupName);

                } catch (Exception e) {

                }
            }
        }

        //trash questions
        String defaultQuestion = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Phasellus rhoncus velit eget lacinia auctor.\n" +
                "Suspendisse quis orci nibh. Sed venenatis mauris sit amet convallis dapibus. Integer ultricies " +
                "volutpat mollis. Suspendisse quis neque ligula. Donec pulvinar dui eu nibh dictum pharetra. Nulla " +
                "varius metus ex, vitae malesuada libero gravida vel. Sed dui ipsum, pellentesque eget mi at, " +
                "tincidunt fermentum sapien. Vivamus in convallis mi, ac consectetur lacus. ";
        for (int i = 0; i < EVIL_GROUPS; i++) {
            String groupName = "Z.TRASH.GROUP." + String.format("%06d",rnd.nextInt(EVIL_GROUPS));
            group = groupRepository.getByName(groupName);
            for (int j=0;j<TRASH_QUESTIONS_PER_GROUPS;j++) {
                question = new Question();
                question.setBody(defaultQuestion + " " + groupName + " j" + "?");
                question.setTeacherComment("Teachers comment");
                question.setReviewComment("Review comment");
                questionRepository.add(question);
                question.setGroupOfQuestion(group);
                question.getOptions().add(new QuestionAnswerOption("A", Boolean.TRUE));
                question.getOptions().add(new QuestionAnswerOption("B", Boolean.FALSE));
                question.getOptions().add(new QuestionAnswerOption("C", Boolean.FALSE));
                question.getOptions().add(new QuestionAnswerOption("D", Boolean.FALSE));
                questionRepository.merge(question);
                System.out.println(question + " g=" + question.getGroupOfQuestion());
            }
        }

        bm.stop();
        model.addAttribute("creationTime", bm.getDifferce());
        return "DONE " + String.valueOf(bm.getDifferce());
    }

    @RequestMapping(value = "/dev/happy_gen")
    @ResponseBody
    public String happyGen(Model model) {
        Benchmark bm = new Benchmark();
        bm.start();

        User user;
        Group group;
        Question question;
        Exam exam;

        user = new User();
        user.setEmail("admin");
        user.setBlocked(Boolean.FALSE);
        user.setPassword("12345");

        userRepository.add(user);

        user = new User();
        user.setEmail("blocked");
        user.setBlocked(Boolean.TRUE);
        user.setPassword("12345");

        userRepository.add(user);

        user = new User();
        user.setEmail("teacher_java");
        user.setBlocked(Boolean.FALSE);
        user.setPassword("12345");

        userRepository.add(user);

        user = new User();
        user.setEmail("teacher_sql");
        user.setBlocked(Boolean.FALSE);
        user.setPassword("12345");

        userRepository.add(user);

        group = new Group();
        group.setGroupName("JAVA.BEGINNER.CORE.EXCEPTION");
        groupRepository.add(group);

        group = new Group();
        group.setGroupName("JAVA.BEGINNER.CORE.STRING");
        groupRepository.add(group);

        group = new Group();
        group.setGroupName("SQL.BEGINNER");
        groupRepository.add(group);

        group = new Group();
        group.setGroupName("DEMO");
        groupRepository.add(group);

        groupService.addUserToGroup("teacher_java","JAVA.BEGINNER.CORE.EXCEPTION");
        groupService.addUserToGroup("teacher_java","JAVA.BEGINNER.CORE.STRING");
        groupService.addUserToGroup("teacher_java","DEMO");
        groupService.addUserToGroup("teacher_sql","SQL.BEGINNER");
        groupService.addUserToGroup("teacher_sql","DEMO");

        for (int i=0;i<10;i++) {
            user = new User();
            user.setEmail("student0"+i);
            user.setBlocked(Boolean.FALSE);
            user.setPassword("12345");
            userRepository.add(user);
        }

        question = new Question();
        question.setTeacherComment("Java. Промышленное программирование");
        question.setReviewComment("Метод substring(i, j) извлекает подстроку из вызывающей строки, " +
                "начиная с символа в позиции i и заканчивая символом в позиции j , не включая " +
                "его. Первый символ строки находится в позиции 0.");
        question.setBody("Дан код:\n" +
                "public class Quest1 {\n" +
                " public static void main(String[] args) {\n" +
                " String str = new String(\"java\");\n" +
                " int i=1;\n" +
                " char j=3;\n" +
                " System.out.println(str.substring(i,j));\n" +
                " }\n" +
                "}\n" +
                "В результате при компиляции и запуске будет выведено:");
        question.getOptions().add(new QuestionAnswerOption("ja",Boolean.FALSE));
        question.getOptions().add(new QuestionAnswerOption("av",Boolean.TRUE));
        question.getOptions().add(new QuestionAnswerOption("ava",Boolean.FALSE));
        question.getOptions().add(new QuestionAnswerOption("jav",Boolean.FALSE));
        question.getOptions().add(new QuestionAnswerOption("ошибка компиляции: заданы некорректные параметры для метода " +
                "substring()",Boolean.FALSE));
        question.setCorrectOptionsCountAdvise(Boolean.TRUE);


        question.setGroupOfQuestion(groupRepository.getByName("JAVA.BEGINNER.CORE.STRING"));

        questionRepository.add(question);
        Long Q00 = question.getId();

        question = new Question();
        question.setTeacherComment("Java. Промышленное программирование");
        question.setReviewComment("0x74 - симовл t, нет правильного ответа.");
        question.setBody("public class Quest4 {\n" +
                " public static void main(String[] args) {\n" +
                " String str=\"ava\";\n" +
                " char ch=0x74; // 74 - это код символа 'J'\n" +
                " str=ch+str;\n" +
                " System.out.print(str);\n" +
                "}}\n");
        question.getOptions().add(new QuestionAnswerOption("74ava",Boolean.FALSE));
        question.getOptions().add(new QuestionAnswerOption("Java",Boolean.FALSE));
        question.getOptions().add(new QuestionAnswerOption("ошибка компиляции: недопустимая операция ch+str",Boolean.FALSE));
        question.getOptions().add(new QuestionAnswerOption("ошибка компиляции: недопустимое объявление char ch=0x74",Boolean.FALSE));
        question.getOptions().add(new QuestionAnswerOption("нет правильного ответа",Boolean.TRUE));
        question.setCorrectOptionsCountAdvise(Boolean.TRUE);

        question.setGroupOfQuestion(groupRepository.getByName("JAVA.BEGINNER.CORE.STRING"));

        questionRepository.add(question);
        Long Q01 = question.getId();

        question = new Question();
        question.setTeacherComment("Java. Промышленное программирование");
        question.setReviewComment("Блок try может завершаться инструкцией catch или finally. В данном\n" +
                "случае во избежание ошибки компиляции необходимо поставить инструкцию\n" +
                "catch(java. io. IOException e) , т.к. метод write() способен генериро-\n" +
                "вать исключение, которое сам не обрабатывает. Метод inc() возвращает значе-\n" +
                "ние, поэтому необходимо завершить код метода инструкцией return counter.\n" +
                "Так как в вопросе предлагалось выбрать два правильных ответа, то возможное до-\n" +
                "бавление в код инструкции finally не представляется возможным.");
        question.setBody("class Quest1{\n" +
                " int counter;\n" +
                " java.io.OutputStream out;\n" +
                " Quest1(){/* инициализация out */}\n" +
                " float inc() {\n" +
                "  try { counter++;\n" +
                "   out.write(counter); }\n" +
                "   //комментарий\n" +
                "}}\n" +
                "Какой код достаточно добавить в метод inc() вместо комментария, чтобы ком-\n" +
                "пиляция прошла без ошибок?");
        question.getOptions().add(new QuestionAnswerOption("catch (java.io.OutputStreamException e){};",Boolean.FALSE));
        question.getOptions().add(new QuestionAnswerOption("catch (java.io.IOException e){};",Boolean.TRUE));
        question.getOptions().add(new QuestionAnswerOption("catch (java.io.OutputException e){};",Boolean.FALSE));
        question.getOptions().add(new QuestionAnswerOption("finally{};",Boolean.FALSE));
        question.getOptions().add(new QuestionAnswerOption("return counter;",Boolean.TRUE));
        question.getOptions().add(new QuestionAnswerOption("return;",Boolean.FALSE));
        question.setCorrectOptionsCountAdvise(Boolean.TRUE);


        question.setGroupOfQuestion(groupRepository.getByName("JAVA.BEGINNER.CORE.EXCEPTION"));

        questionRepository.add(question);
        Long Q02 = question.getId();

        question = new Question();
        question.setTeacherComment("Demo");
        question.setReviewComment("-");
        question.setBody("Это пример вопроса.\n" +
                "Пользователю не дается никаких подсказок.");
        question.getOptions().add(new QuestionAnswerOption("Ответ 1:",Boolean.FALSE));
        question.getOptions().add(new QuestionAnswerOption("Ответ 2:",Boolean.TRUE));
        question.getOptions().add(new QuestionAnswerOption("Ответ 3:",Boolean.FALSE));
        question.getOptions().add(new QuestionAnswerOption("Ответ 4:",Boolean.TRUE));
        question.setCorrectOptionsCountAdvise(Boolean.FALSE);

        question.setGroupOfQuestion(groupRepository.getByName("DEMO"));

        questionRepository.add(question);
        Long Q03 = question.getId();

        question = new Question();
        question.setTeacherComment("Demo");
        question.setReviewComment("-");
        question.setBody("В этом вопросе пользователю дается подсказка о количестве правильных ответов (1 ответ правильный):");
        question.getOptions().add(new QuestionAnswerOption("Ответ 1:",Boolean.TRUE));
        question.getOptions().add(new QuestionAnswerOption("Ответ 2:",Boolean.FALSE));
        question.getOptions().add(new QuestionAnswerOption("Ответ 3:",Boolean.FALSE));
        question.getOptions().add(new QuestionAnswerOption("Ответ 4:",Boolean.FALSE));
        question.setCorrectOptionsCountAdvise(Boolean.TRUE);

        question.setGroupOfQuestion(groupRepository.getByName("DEMO"));

        questionRepository.add(question);
        Long Q04 = question.getId();

        question = new Question();
        question.setTeacherComment("Demo");
        question.setReviewComment("-");
        question.setBody("В этом вопросе пользователю дается подсказка о количестве правильных ответов (2 ответа правильные):");
        question.getOptions().add(new QuestionAnswerOption("Ответ 1:",Boolean.TRUE));
        question.getOptions().add(new QuestionAnswerOption("Ответ 2:",Boolean.TRUE));
        question.getOptions().add(new QuestionAnswerOption("Ответ 3:",Boolean.FALSE));
        question.getOptions().add(new QuestionAnswerOption("Ответ 4:",Boolean.FALSE));
        question.setCorrectOptionsCountAdvise(Boolean.TRUE);

        question.setGroupOfQuestion(groupRepository.getByName("DEMO"));

        questionRepository.add(question);
        Long Q05 = question.getId();

        question = new Question();
        question.setTeacherComment("Demo");
        question.setReviewComment("-");
        question.setBody("Нет правильных ответов, но подсказка стоит. Без палева...:");
        question.getOptions().add(new QuestionAnswerOption("Ответ 1:",Boolean.FALSE));
        question.getOptions().add(new QuestionAnswerOption("Ответ 2:",Boolean.FALSE));
        question.getOptions().add(new QuestionAnswerOption("Ответ 3:",Boolean.FALSE));
        question.getOptions().add(new QuestionAnswerOption("Ответ 4:",Boolean.FALSE));
        question.setCorrectOptionsCountAdvise(Boolean.TRUE);

        question.setGroupOfQuestion(groupRepository.getByName("DEMO"));

        questionRepository.add(question);
        Long Q06 = question.getId();

        exam = new Exam();
        exam.setName("Too late ...");
        exam.setTestTimeInMinutes(30);
        exam.setShowResults(Boolean.TRUE);
        exam.setCreator(userRepository.getByMail("teacher_java"));
        exam.getRecievers().add(userRepository.getByMail("student00"));
        exam.getRecievers().add(userRepository.getByMail("student01"));
        exam.getQuestions().add(questionRepository.getById(Q00));
        exam.getQuestions().add(questionRepository.getById(Q01));
        exam.getQuestions().add(questionRepository.getById(Q02));
        exam.getQuestions().add(questionRepository.getById(Q03));
        exam.getQuestions().add(questionRepository.getById(Q04));
        exam.getQuestions().add(questionRepository.getById(Q05));
        exam.getQuestions().add(questionRepository.getById(Q06));
        exam.setStartWindowOpen(new Date(System.currentTimeMillis()+10000));
        examService.saveNewExam(exam);
        exam.setStartWindowOpen(new Date(0));

        examRepository.merge(exam);

        exam = new Exam();
        exam.setName("Late ...");
        exam.setTestTimeInMinutes(45);
        exam.setShowResults(Boolean.TRUE);
        exam.setCreator(userRepository.getByMail("teacher_java"));
        exam.getRecievers().add(userRepository.getByMail("student00"));
        exam.getRecievers().add(userRepository.getByMail("student01"));
        exam.getQuestions().add(questionRepository.getById(Q00));
        exam.getQuestions().add(questionRepository.getById(Q01));
        exam.getQuestions().add(questionRepository.getById(Q02));
        exam.getQuestions().add(questionRepository.getById(Q03));
        exam.getQuestions().add(questionRepository.getById(Q04));
        exam.getQuestions().add(questionRepository.getById(Q05));
        exam.getQuestions().add(questionRepository.getById(Q06));
        exam.setStartWindowOpen(new Date(System.currentTimeMillis()+10000));
        examService.saveNewExam(exam);
        exam.setStartWindowOpen(new Date(System.currentTimeMillis()-60*16*1000));
        examRepository.merge(exam);

        exam = new Exam();
        exam.setName("Future");
        exam.setTestTimeInMinutes(45);
        exam.setShowResults(Boolean.TRUE);
        exam.setCreator(userRepository.getByMail("teacher_java"));
        exam.getRecievers().add(userRepository.getByMail("student00"));
        exam.getRecievers().add(userRepository.getByMail("student01"));
        exam.getQuestions().add(questionRepository.getById(Q00));
        exam.getQuestions().add(questionRepository.getById(Q01));
        exam.getQuestions().add(questionRepository.getById(Q02));
        exam.getQuestions().add(questionRepository.getById(Q03));
        exam.getQuestions().add(questionRepository.getById(Q04));
        exam.getQuestions().add(questionRepository.getById(Q05));
        exam.getQuestions().add(questionRepository.getById(Q06));
        exam.setStartWindowOpen(new Date(System.currentTimeMillis()+3*24*60*60*1000));
        examService.saveNewExam(exam);

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
