package ua.epam.rd.service.mail;

import ua.epam.rd.domain.Invite;

import java.util.Date;

/**
 * Created by Mykhaylo Gnylorybov on 26.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
public class MailComposer {
    public static String serverURL = "http://localhost:8080/";
    public static String restorePageURI = "restore";

    public String RestorePasswordTokenSubject() {
        return "Password restore confirmation";
    }

    public String RestorePasswordTokenLetter(String token) {
        return serverURL + restorePageURI + "?token=" + token;
    }

    public String NewPasswordSubject() {
        return "New password";
    }

    public String NewPasswordLetter(String password) {
        return "Your new password for " + serverURL + " " + password;
    }

    public String RegistrationSubject() {
        return "Registration in " + serverURL;
    }

    public String RegistrationLetter(String login, String password) {
        return "Your login: " + login + ", password: " + password;
    }

    public String ExamInviteSubject() {
        return "Remote testing";
    }

    public String ExamInviteLetter(Date startDate, int lateTime) {
        return "The test is available for passage . Access to the test will be open " + startDate.toString() +", GMT +2";
    }

    public String ExamFinishedSubject() {
        return "Test finished";
    }

    public String ExamFinishedLetter() {
        return "Thank you for participating in our online test";
    }
}
