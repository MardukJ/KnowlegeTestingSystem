package ua.epam.rd.service.mail;

import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 25.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
public interface MailService {
    void sendMailNoConfirmation(String address, String subj, String message);

    List<String> getHistory();
}