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

    int getAllTotalPages();

    List<User> getAllFromPage(int page);

    List<User> getAllNow();

    void sendRestorePasswordToken(String mail);

    User validateRestorePasswordToken(String token);

    User getUserInfo(String mail);

    void blockUser(Long id);

    void unblockUser(Long id);
}
