package ua.epam.rd.domain;

import javax.persistence.*;

/**
 * Created by Mykhaylo Gnylorybov on 02.05.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
@Entity
@Table (name = "questions_answers_options")
public class  QuestionAnswerOption {
    @Id
    @GeneratedValue
    @Column (name = "id_qao")
    Long id;

    @Column (name = "text")
    String optionText;

    @Column (name = "correct_answer")
    Boolean correctAnswer= Boolean.FALSE;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "id_question")
    Question questionForOption;

    public QuestionAnswerOption() {
    }

    public QuestionAnswerOption(String optionText, Boolean correctAnswer) {
        this.optionText = optionText;
        this.correctAnswer = correctAnswer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        throw new UnsupportedOperationException();
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public Boolean getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Question getQuestionForOption() {
        return questionForOption;
    }

    public void setQuestionForOption(Question questionForOption) {
        this.questionForOption = questionForOption;
    }

    public boolean verifyMe() {
        if (optionText==null) return false;
        if (correctAnswer==null) return false;
        if (optionText.length()==0) return false;
        return true;
    }
}
