package ua.epam.rd.domain;

import javax.persistence.*;

/**
 * Created by Mykhaylo Gnylorybov on 10.05.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
@Entity
@Table (name = "invite_answers")
public class InviteAnswer {
    @Id
    @GeneratedValue
    @Column(name = "id_invite_answer")
    private Long id;

    @Column (name = "choise")
    private Boolean userChoise = Boolean.FALSE;

    //WTF? EAGER?
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "id_qao")
    private QuestionAnswerOption questionAnswerOption;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "id_invite")
    private Invite parentInvite;

    public InviteAnswer() {
    }

    public InviteAnswer(QuestionAnswerOption questionAnswerOption, Invite parentInvite) {
        this.questionAnswerOption = questionAnswerOption;
        this.parentInvite = parentInvite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getUserChoise() {
        return userChoise;
    }

    public void setUserChoise(Boolean userChoise) {
        this.userChoise = userChoise;
    }

    public QuestionAnswerOption getQuestionAnswerOption() {
        return questionAnswerOption;
    }

    public void setQuestionAnswerOption(QuestionAnswerOption questionAnswerOption) {
        this.questionAnswerOption = questionAnswerOption;
    }

    public Invite getParentInvite() {
        return parentInvite;
    }

    public void setParentInvite(Invite parentInvite) {
        this.parentInvite = parentInvite;
    }
}
