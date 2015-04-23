package ua.epam.rd.service;

import ua.epam.rd.domain.User;

import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 24.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
public interface UserService {
    Long registerNew(User newUser);

    User checkPasswordAndGetUser(String mail, String password);

    long getAllTotalPages();

    List<User> getAllPage(int page);
}
