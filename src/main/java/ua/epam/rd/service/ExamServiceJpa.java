package ua.epam.rd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.rd.domain.*;
import ua.epam.rd.repository.ExamRepository;
import ua.epam.rd.repository.InviteRepostiory;
import ua.epam.rd.service.mail.MailComposer;
import ua.epam.rd.service.mail.MailService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 06.05.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
@Service
public class ExamServiceJPA implements ExamService {
    @Autowired
    ExamRepository examRepository;

    @Autowired
    MailService mailService;

    @Autowired
    InviteRepostiory inviteRepostiory;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long saveNewExam(Exam exam) {
        if (exam.verifyMe()!=null) {
            throw new IllegalArgumentException(exam.verifyMe());
        }
        for (User u: exam.getRecievers()) {
            Invite i = new Invite(u,exam);
            exam.getInvites().add(i);

            //mail notification
            MailComposer mailComposer = new MailComposer();
            String subject = mailComposer.ExamInviteSubject();
            String letter = mailComposer.ExamInviteLetter(exam.getStartWindowOpen(), exam.getMaxLateTimeInMinutes());
            mailService.sendMailNoConfirmation(u.getEmail(), subject, letter);
        }
        return examRepository.add(exam);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Exam getById(Long idExam) {
//        Exam result =  examRepository.getById(idExam);
        Exam result = checkTimeoutAndStatus(idExam);
        result.getCreator().getId();
        if (result.getInvites()!=null) {
            for (Invite i: result.getInvites()) {
                i.getInviteReceiver().getId();
            }
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Exam> getExamsOfUser(Long idUser) {
        List<Exam> exams =examRepository.getExamsOfUser(idUser);
        if (exams!=null) {
            ArrayList <Exam> result = new ArrayList<>(exams.size());
            for (Exam e:exams) {
                result.add(checkTimeoutAndStatus(e.getId()));
            }
            return result;
        } else {
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Exam checkTimeoutAndStatus(Long idExam) {
        Exam exam = examRepository.getById(idExam);
        if (exam==null) throw new IllegalArgumentException();
        if (exam.getStatus().equals(ExamStatus.FINISHED) || exam.getStatus().equals(ExamStatus.CANCELED)) {
            //no check required
            return exam;
        }
        boolean iMadeSomeChanges = false;
        boolean hasInvitesInProgress = false;
        boolean hasInvitesFinished = false;
        boolean hasInvitesNew = false;
        for (Invite i : exam.getInvites()) {
            if (!i.getInviteStatus().equals(InviteStatus.CANCELED) && !i.getInviteStatus().equals(InviteStatus.FINISHED)) {
                if (i.checkTimeout()) {
                    i.getScore();
                    iMadeSomeChanges=true;
                }
            }
            if (i.getInviteStatus().equals(InviteStatus.NEW)) {
                hasInvitesNew =true;
            } else if (i.getInviteStatus().equals(InviteStatus.IN_PROGRESS)) {
                hasInvitesInProgress=true;
            } else if (i.getInviteStatus().equals(InviteStatus.FINISHED) || i.getInviteStatus().equals(InviteStatus.NO_SHOW)) {
                hasInvitesFinished=true;
            }
        }
        if (hasInvitesNew ==false && hasInvitesInProgress==false && hasInvitesFinished==true) {
            exam.setStatus(ExamStatus.FINISHED);
            iMadeSomeChanges=true;
        } else if (hasInvitesNew ==true && hasInvitesInProgress==false && hasInvitesFinished==false) {
        } else {
            exam.setStatus(ExamStatus.IN_PROGRESS);
            iMadeSomeChanges=true;
        }
        if (iMadeSomeChanges==true) {
            examRepository.merge(exam);
            return examRepository.getById(exam.getId());
        }
        return exam;
    }
}
