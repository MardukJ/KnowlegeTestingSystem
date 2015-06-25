package ua.epam.rd.repository;

import org.springframework.stereotype.Repository;
import ua.epam.rd.domain.Invite;

import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 10.05.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
public interface InviteRepostiory {
    Long add(Invite invite);

    Invite getById (Long id);

    List<Invite> getAll();

    void merge(Invite invite);

    void delete(Long id);
}
