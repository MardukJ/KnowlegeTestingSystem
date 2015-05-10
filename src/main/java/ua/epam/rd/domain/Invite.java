package ua.epam.rd.domain;

import javax.persistence.*;
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

    @OneToMany (fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "parentInvite")
    List <InviteAnswer> inviteAnswers;

    @Enumerated(EnumType.STRING)
    InviteStatus inviteStatus = InviteStatus.NEW;

    @Column(name = "result")
    Integer result;
}
