package ua.epam.rd.domain;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 10.05.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
@Entity
@Table (name = "invite")
public class Invite {
    @Id
    @GeneratedValue
    @Column(name = "id_invite")
    Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "id_user")
    User inviteReceiver;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "id_exam")
    Exam inviteExam;

    @OneToMany (fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "parentInvite", cascade = CascadeType.ALL)
    List <InviteAnswer> inviteAnswers = new LinkedList<InviteAnswer>();

    @Enumerated(EnumType.STRING)
    InviteStatus inviteStatus = InviteStatus.NEW;

    @Column(name = "result")
    Integer result = new Integer(0);

    @Column(name = "max_result")
    Integer maxResult = new Integer(0);

    public Invite() {
    }

    public Invite(User inviteReceiver, Exam inviteExam) {
        this.inviteReceiver = inviteReceiver;
        this.inviteExam = inviteExam;
        for (Question q : inviteExam.getQuestions()) {
            for (QuestionAnswerOption qao: q.getOptions()) {
                InviteAnswer ia = new InviteAnswer(qao, this);
                inviteAnswers.add(ia);
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        throw new UnsupportedOperationException();
    }

    public User getInviteReceiver() {
        return inviteReceiver;
    }

    public void setInviteReceiver(User inviteReceiver) {
        this.inviteReceiver = inviteReceiver;
    }

    public Exam getInviteExam() {
        return inviteExam;
    }

    public void setInviteExam(Exam inviteExam) {
        this.inviteExam = inviteExam;
    }

    public List<InviteAnswer> getInviteAnswers() {
        return inviteAnswers;
    }

    public void setInviteAnswers(List<InviteAnswer> inviteAnswers) {
        throw new UnsupportedOperationException();
    }

    public InviteStatus getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(InviteStatus inviteStatus) {
        this.inviteStatus = inviteStatus;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        throw new UnsupportedOperationException();
    }

    public Integer getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(Integer maxResult) {
        this.maxResult = maxResult;
    }

    //for jst usage
    public Boolean getAnswerForQuestionOption(String idQAOs) {
        Long idQAO = Long.valueOf(idQAOs);
        for (InviteAnswer a: inviteAnswers) {
            if (a.getQuestionAnswerOption().getId().equals(idQAO)) {
                return a.getUserChoise();
            }
        }
        throw  new IllegalStateException();
    }

    //for scoring algorithm
    public Boolean getAnswerForQuestionOption(Long idQAO) {
        for (InviteAnswer a: inviteAnswers) {
            if (a.getQuestionAnswerOption().getId().equals(idQAO)) {
                return a.getUserChoise();
            }
        }
        throw  new IllegalStateException();
    }

    //for result saving
    public void setAnswerForQuestionOption(Long idQAO, Boolean choice) {
        for (InviteAnswer a: inviteAnswers) {
            if (a.getQuestionAnswerOption().getId().equals(idQAO)) {
                a.setUserChoise(choice);
                return;
            }
        }
        throw  new IllegalStateException();
    }

    public int getScore() {
        result=0;
        maxResult=0;

        if (checkTimeout() && inviteStatus.equals(InviteStatus.NEW)) {
            inviteStatus=InviteStatus.NO_SHOW;
            return result;
        }

        if (inviteStatus.equals(InviteStatus.FINISHED) || inviteStatus.equals(InviteStatus.CANCELED))
            throw new IllegalStateException("Invalid invite status, getScore(),invite = " + getId());
        switch (inviteExam.getScoringAlgorithm()) {
            case A:
                maxResult=inviteExam.getQuestions().size();

                for (Question q: inviteExam.getQuestions()) {
                    boolean correct = true;
                    for (QuestionAnswerOption o: q.getOptions()) {
                        Boolean correctAnswer = o.correctAnswer;
                        Boolean userAnswer = getAnswerForQuestionOption(o.getId());
                        if (!correctAnswer.equals(userAnswer)) {
                            correct = false;
                            break;
                        }
                    }
                    if (correct) result ++;
                }

                break;
            case B:
                for (Question q: inviteExam.getQuestions()) {
                    for (QuestionAnswerOption qao: q.getOptions()) {
                        if (qao.correctAnswer.equals(Boolean.TRUE)) {
                            maxResult++;
                        }
                    }
                }

                for (Question q: inviteExam.getQuestions()) {
                    int questionResult = 0;
                    for (QuestionAnswerOption o: q.getOptions()) {
                        Boolean correctAnswer = o.correctAnswer;
                        Boolean userAnswer = getAnswerForQuestionOption(o.getId());
                        if (userAnswer.equals(Boolean.TRUE)) {
                            if (correctAnswer.equals(userAnswer)) {
                                questionResult++;
                            } else {
                                questionResult--;
                            }
                        }
                    }
                    if (questionResult>0) result+=questionResult;
                }
                break;
            default:
                throw new IllegalStateException("Unknown scoring algorithm,Invite.getScore() , exam id=" + inviteExam.getId());
        }
        inviteStatus=InviteStatus.FINISHED;
        return result;
    }

    public boolean checkTimeout() {
        System.out.println("invite.checkTimeout");
        return true;
    };
}
