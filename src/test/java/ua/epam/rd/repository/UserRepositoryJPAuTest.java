package ua.epam.rd.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.epam.rd.domain.User;
import ua.epam.rd.template.UTRepositoryTestsTemplate;

import static org.junit.Assert.*;

public class UserRepositoryJPAuTest extends UTRepositoryTestsTemplate {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testAdd() {
        User user = new User();
        user.setEmail("Ы");
        user.setPassword("Ы");
        user.setBlocked(Boolean.FALSE);

        Long id = userRepository.add(user);
        System.out.println(id);

        assertNotNull(id);
    }

    @Test
    public void testGetById() {
        User lost = new User();
        lost.setEmail("Ы");
        lost.setPassword("Ы");
        lost.setBlocked(Boolean.FALSE);

        Long idLost = userRepository.add(lost);

        User found = userRepository.getById(idLost);

        assertEquals(lost, found);
    }

    @Test
    public void testGetByMailSaveAndFound() {
        String mail = "Ы!testGetByMailSaveAndFound";
        User lost = new User();
        lost.setEmail(mail);
        lost.setPassword("Ы");
        lost.setBlocked(Boolean.FALSE);
        userRepository.add(lost);

        User found = userRepository.getByMail(mail);
        assertEquals(lost, found);
    }

    @Test
    public void testGetByMailNotFound() {
        String mail = "Ы!testGetByMailNotFound";
        User found = userRepository.getByMail(mail);
        assertNull(found);
    }

    @Test
    public void testGetAll() {
        int initialSize = userRepository.getAll().size();
        System.out.println("initialSize " + initialSize);

        //+2 users
        User user = new User();
        user.setEmail("Ы");
        user.setPassword("Ы");
        user.setBlocked(Boolean.FALSE);
        userRepository.add(user);

        user = new User();
        user.setEmail("Ы");
        user.setPassword("Ы");
        user.setBlocked(Boolean.FALSE);
        userRepository.add(user);

        int finalSize = userRepository.getAll().size();
        System.out.println("finalSize " + finalSize);

        assertEquals(initialSize + 2, finalSize);
    }

    @Test
    //no exception check only
    public void testMerge() {
        String mail = "123";

        User user = new User();
        user.setEmail("Ы");
        user.setPassword("Ы");
        user.setBlocked(Boolean.FALSE);

        Long id = userRepository.add(user);
        userRepository.merge(user);
    }
}
