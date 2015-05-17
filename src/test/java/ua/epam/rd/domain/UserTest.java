package ua.epam.rd.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Mykhaylo Gnylorybov on 24.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
public class UserTest {

    @Test
    public void testEncryptDigital() throws Exception {
        User user = new User();
        assertEquals("e10adc3949ba59abbe56e057f20f883e", user.encrypt("123456"));
    }

    @Test
    public void testEncryptEnglish() throws Exception {
        User user = new User();
        assertEquals("8b1a9953c4611296a827abf8c47804d7", user.encrypt("Hello"));
    }

    @Test
    public void testTryPasswordCorrect() {
        User user = new User();
        user.setPassword("Aa123");
        boolean result = user.tryPassword("Aa123");
        System.out.println(result);
        assertTrue(result);
    }

    @Test
    public void testTryPasswordIncorrect() {
        User user = new User();
        user.setPassword("Aa123");
        boolean result = user.tryPassword("aa123");
        System.out.println(result);
        assertFalse(result);
    }

    @Test
    public void testVerifyMailGood() throws Exception {
        List <String> goodMails = new ArrayList<>();
        goodMails.add("a@b.ru");
        goodMails.add("bob.marley@dot.com");
        goodMails.add("vasya321@a.hren.znaem.gde.ru");

        for (String m: goodMails) {
            User u = new User();
            u.setEmail(m);
            assertNull(u.verifyMail());
        }
    }

    @Test
    public void testVerifyMailBad() throws Exception {
        List <String> badMails = new ArrayList<>();
        badMails.add("a.b.ru");
        badMails.add(".a@b.ru");
        badMails.add(".a@ru");
        badMails.add(".a@ru.");

        for (String m: badMails) {
            User u = new User();
            u.setEmail(m);
            assertNotNull(u.verifyMail());
        }
    }
}