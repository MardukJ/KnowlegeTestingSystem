package ua.epam.rd.domain;

import javax.persistence.*;
import java.util.*;

/**
 * Created by Mykhaylo Gnylorybov on 02.05.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue
    @Column (name = "id_question")
    private Long id;

    @Column (name = "body", length = 2000)
    //question itself
    private String body;

    @Column (name = "teachers_comment", length = 1000)
    //comment for teacher
    private String teacherComment;

    @Column (name = "review_comment", length = 1000)
    //comment for review
    private String reviewComment;

    @Column (name = "ver")
    private Long version=new Long(0);

    @Column (name = "outdated")
    private Boolean outdated = Boolean.FALSE;

    @Column (name = "options_advise")
    private Boolean correctOptionsCountAdvise = Boolean.FALSE;

    //answers
    @OneToMany (mappedBy = "questionForOption", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<QuestionAnswerOption> options = new LinkedList<QuestionAnswerOption>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_group")
    private Group groupOfQuestion;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTeacherComment() {
        return teacherComment;
    }

    public void setTeacherComment(String teacherComment) {
        this.teacherComment = teacherComment;
    }

    public String getReviewComment() {
        return reviewComment;
    }

    public void setReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Group getGroupOfQuestion() {
        return groupOfQuestion;
    }

    public void setGroupOfQuestion(Group groupOfQuestion) {
        this.groupOfQuestion = groupOfQuestion;
    }

    public List<QuestionAnswerOption> getOptions() {
        return options;
    }

    public void setOptions(List<QuestionAnswerOption> options) {
        throw new UnsupportedOperationException();
    }

    public Boolean getCorrectOptionsCountAdvise() {
        return correctOptionsCountAdvise;
    }

    public void setCorrectOptionsCountAdvise(Boolean correctOptionsCountAdvise) {
        this.correctOptionsCountAdvise = correctOptionsCountAdvise;
    }

    public void invalidate() {
        outdated=Boolean.TRUE;
    }

    public String verifyMe() {
        if ((body==null) || (body.length()==0)) {
            return "No question";
        }
        System.out.println("OK1");
        if (options==null) {
            return "No answers";
        }
        int optionsCount = 0;
        for (QuestionAnswerOption o : options) {
            if (o.getOptionText().length()>0) optionsCount++;
        }
        if (optionsCount == 0) return  "No answers";
        System.out.println("OK2");
        return null;//"question verification result";
    }

    public void removeVoidOptions() {
        Iterator <QuestionAnswerOption> it = options.iterator();
        while (it.hasNext()){
            QuestionAnswerOption qao = it.next();
            if (!qao.verifyMe()) {
                it.remove();
            }
        }
        //remove duplicates
        //N.B. answers can be different, but options the same -> cans use Set
    }

    public int getCountPositiveAnswers() {
        int counter = 0;
        for (QuestionAnswerOption qao: options) {
            if (qao.correctAnswer.equals(Boolean.TRUE))
                counter++;
        }
        return counter;
    }
}
