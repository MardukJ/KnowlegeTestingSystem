package ua.epam.rd.domain;

import javax.persistence.*;

/**
 * Created by Mykhaylo Gnylorybov on 02.05.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
@Entity
@Table (name = "questions_answers_options")
public class QuestionAnswerOption {
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
}
