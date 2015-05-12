package ua.epam.rd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.rd.domain.Invite;
import ua.epam.rd.repository.InviteRepostiory;

import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 10.05.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */

public interface InviteService {
    Invite getByIdWExamAndUser (Long idInvite);

    void save(Invite invite);

    Invite forceFinish(Long idInvite);
}
