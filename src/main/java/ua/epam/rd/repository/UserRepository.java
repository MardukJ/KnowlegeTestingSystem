package ua.epam.rd.repository;

import ua.epam.rd.domain.User;

import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 23.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 * <p>
 * UserRepository interface
 */
public interface UserRepository {
    Long add(User user);

    User getById(Long id);

    User getByMail(String email);

    List<User> getAll();

    void merge(User user);

    void delete(Long id);

    long getTotalEntry();

    List<User> getEntryInRange(int first, int size);
}
