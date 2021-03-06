package ua.epam.rd.service.mail;

//import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mykhaylo Gnylorybov on 25.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 * <p>
 * Mailing system prototype
 */
@Component
@Qualifier("mailService")
public class MailServiceMuck implements MailService {
    //private static final Logger logger = Logger.getLogger(MailService.class);

    ArrayList<String> history = new ArrayList<String>();

    @Override
    public void sendMailNoConfirmation(String address, String subj, String message) {
        //logger.error("MAIL address  = " + address + " msg = " + message);
        System.out.println("MAIL address  = " + address + " subject = " + subj + " msg = " + message);
        history.add("MAIL address  = " + address + " <BR>subject = " + subj + " <BR>msg = " + message + "<BR>");
    }

    public List<String> getHistory() {
        return history;
    }
}
