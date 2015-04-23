package ua.epam.rd.domain;

import org.junit.Test;

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
}