package ua.epam.rd.service.mail;

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

    ;

    public String RestorePasswordTokenLetter(String token) {
        return serverURL + restorePageURI + "?token=" + token;
    }

    ;

    public String NewPasswordSubject() {
        return "New password";
    }

    ;

    public String NewPasswordLetter(String password) {
        return "Your new password for " + serverURL + " " + password;
    }

    ;
}
