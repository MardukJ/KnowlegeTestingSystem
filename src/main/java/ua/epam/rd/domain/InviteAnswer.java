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

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "id_qao")
    private QuestionAnswerOption questionAnswerOption;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "id_invite")
    private Invite parentInvite;
}
