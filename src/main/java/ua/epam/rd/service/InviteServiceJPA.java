package ua.epam.rd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.rd.domain.Invite;
import ua.epam.rd.domain.InviteStatus;
import ua.epam.rd.repository.InviteRepostiory;
import ua.epam.rd.service.mail.MailComposer;
import ua.epam.rd.service.mail.MailService;

/**
 * Created by Mykhaylo Gnylorybov on 10.05.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */

@Service
public class InviteServiceJPA implements InviteService {
    @Autowired
    private InviteRepostiory inviteRepostitory;

    @Autowired
    MailService mailService;

    @Override
    @Transactional (propagation = Propagation.SUPPORTS, readOnly = true)
    public Invite getByIdWExamAndUser(Long idInvite) {
        Invite invite = inviteRepostitory.getById(idInvite);
        if (invite!=null) {
            invite.getInviteReceiver().getId();
            invite.getInviteExam().getId();
            invite.getInviteExam().getQuestions().size();
        }
        return invite;
    }

    @Override
    @Transactional (propagation = Propagation.REQUIRED)
    public void save(Invite invite) {
        inviteRepostitory.merge(invite);
    }

    @Override
    @Transactional (propagation = Propagation.REQUIRED)
    public Invite forceFinish(Long idInvite) {
        Invite invite = inviteRepostitory.getById(idInvite);
        if (invite == null) throw new UnsupportedOperationException();
        invite.getScore();

        inviteRepostitory.merge(invite);

        mailService.sendMailNoConfirmation(invite.getInviteReceiver().getEmail(),new MailComposer().ExamFinishedSubject(), new MailComposer().ExamFinishedLetter());

        return invite;
    }
}
