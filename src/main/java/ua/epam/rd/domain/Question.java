package ua.epam.rd.domain;

import javax.persistence.*;
import java.util.List;

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
    Long id;

    @Column (name = "body")
    //question itself
    String body;

    @Column (name = "teachers_comment")
    //comment for teacher
    String teacherComment;

    @Column (name = "review_comment")
    //comment for review
    String reviewComment;

    @Column (name = "ver")
    Long version=new Long(0);

    //answers
    @OneToMany (mappedBy = "questionForOption", fetch = FetchType.EAGER, orphanRemoval = true)
    List <QuestionAnswerOption> options;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_group")
    Group groupOfQuestion;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException("use newVersion() instead");
    }

    public void newVersion() {
        version = new Long(version.longValue() + 1L);
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
}
