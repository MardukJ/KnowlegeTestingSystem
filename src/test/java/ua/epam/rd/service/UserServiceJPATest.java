package ua.epam.rd.service;

import org.junit.Test;
import ua.epam.rd.domain.User;
import ua.epam.rd.repository.UserRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by Mykhaylo Gnylorybov on 24.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
public class UserServiceJPATest {

    //= UserServiceJPA PAGE_SIZE_BY_USER
    private static final long PAGE_SIZE = UserServiceJPA.PAGE_SIZE;

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterNewNullArgument() throws Exception {
        UserRepository mockRepository = mock(UserRepository.class);
        UserService userService = new UserServiceJPA(mockRepository);

        userService.registerNew(null);
    }

    @Test
    public void testRegisterNewAlreadyExist() throws Exception {
        UserRepository mockRepository = mock(UserRepository.class);
        UserService userService = new UserServiceJPA(mockRepository);

        String mail = "1234567890";
        Long id = new Long(-1);

        User user = mock(User.class);
        when(user.getEmail()).thenReturn(mail);
        when(user.getId()).thenReturn(id);

        when(mockRepository.getByMail(mail)).thenReturn(user);

        Long result = userService.registerNew(user);

        verify(mockRepository, times(1)).getByMail(mail);
        verify(mockRepository, never()).add(user);

        assertEquals(id, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterNewVerificationFailed() throws Exception {
        UserRepository mockRepository = mock(UserRepository.class);
        UserService userService = new UserServiceJPA(mockRepository);

        String mail = "1234567890";

        User user = mock(User.class);
        when(user.getEmail()).thenReturn(mail);
        when(user.verifyMe()).thenReturn("Verification Error");

        when(mockRepository.getByMail(mail)).thenReturn(null);

        Long result = userService.registerNew(user);
    }

    @Test
    public void testRegisterNewSuccess() throws Exception {
        UserRepository mockRepository = mock(UserRepository.class);
        UserService userService = new UserServiceJPA(mockRepository);

        String mail = "1234567890";

        User user = mock(User.class);
        when(user.getEmail()).thenReturn(mail);
        when(user.verifyMe()).thenReturn(null);

        when(mockRepository.getByMail(mail)).thenReturn(null);

        Long result = userService.registerNew(user);

        verify(mockRepository, times(1)).add(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckPasswordAndGetUserNoArgument() throws Exception {
        UserRepository mockRepository = mock(UserRepository.class);
        UserService userService = new UserServiceJPA(mockRepository);

        userService.checkPasswordAndGetUser(null, null);
    }

    @Test
    public void testCheckPasswordAndGetUserSuccess() throws Exception {
        UserRepository mockRepository = mock(UserRepository.class);
        UserService userService = new UserServiceJPA(mockRepository);

        String mail = "0987654321";
        String password = "123";

        User user = new User();
        user.setEmail(mail);
        user.setPassword(password);

        when(mockRepository.getByMail(anyString())).thenReturn(user);

        User result = userService.checkPasswordAndGetUser(mail, password);

        verify(mockRepository, times(1)).getByMail(mail);
        assertEquals(user, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckPasswordAndGetUserNoUser() throws Exception {
        UserRepository mockRepository = mock(UserRepository.class);
        UserService userService = new UserServiceJPA(mockRepository);

        String mail = "0987654321";
        String password = "123";

        User user = new User();
        user.setEmail(mail);
        user.setPassword(password);

        when(mockRepository.getByMail(anyString())).thenReturn(null);

        User result = userService.checkPasswordAndGetUser(mail, password);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckPasswordAndGetUserIncorrectPassword() throws Exception {
        UserRepository mockRepository = mock(UserRepository.class);
        UserService userService = new UserServiceJPA(mockRepository);

        String mail = "0987654321";
        String password1 = "123";
        String password2 = "321";

        User user = new User();
        user.setEmail(mail);
        user.setPassword(password1);

        when(mockRepository.getByMail(anyString())).thenReturn(user);

        User result = userService.checkPasswordAndGetUser(mail, password2);
    }

    @Test
    public void testGetAllTotalPagesZero() throws Exception {
        UserRepository mockRepository = mock(UserRepository.class);
        UserService userService = new UserServiceJPA(mockRepository);

        when(mockRepository.getTotalEntry()).thenReturn(0L);

        int correctValue = 1;
        int totaPages = userService.getAllTotalPages();

        assertEquals(totaPages, correctValue);
    }

    @Test
    public void testGetAllTotalPagesOne() throws Exception {
        UserRepository mockRepository = mock(UserRepository.class);
        UserService userService = new UserServiceJPA(mockRepository);

        when(mockRepository.getTotalEntry()).thenReturn(1L);

        int correctValue = 1;
        int totaPages = userService.getAllTotalPages();

        assertEquals(totaPages, correctValue);
    }

    @Test
    public void testGetAllTotalPagesLess() throws Exception {
        UserRepository mockRepository = mock(UserRepository.class);
        UserService userService = new UserServiceJPA(mockRepository);

        when(mockRepository.getTotalEntry()).thenReturn(PAGE_SIZE - 1);

        int correctValue = 1;
        int totaPages = userService.getAllTotalPages();

        assertEquals(totaPages, correctValue);
    }

    @Test
    public void testGetAllTotalPagesEq() throws Exception {
        UserRepository mockRepository = mock(UserRepository.class);
        UserService userService = new UserServiceJPA(mockRepository);

        when(mockRepository.getTotalEntry()).thenReturn(PAGE_SIZE);

        int correctValue = 1;
        int totaPages = userService.getAllTotalPages();

        assertEquals(totaPages, correctValue);
    }

    @Test
    public void testGetAllTotalPagesMore() throws Exception {
        UserRepository mockRepository = mock(UserRepository.class);
        UserService userService = new UserServiceJPA(mockRepository);

        when(mockRepository.getTotalEntry()).thenReturn(PAGE_SIZE + 1);

        int correctValue = 2;
        int totaPages = userService.getAllTotalPages();

        assertEquals(totaPages, correctValue);
    }


    @Test
    public void testGetAllFromPage07() throws Exception {
        UserRepository mockRepository = mock(UserRepository.class);
        UserService userService = new UserServiceJPA(mockRepository);

        userService.getAllFromPage(7);

        verify(mockRepository, times(1)).getEntryInRange((int) (PAGE_SIZE * 6L), (int) PAGE_SIZE);
    }

    @Test
    public void testGetAllFromPage01() throws Exception {
        UserRepository mockRepository = mock(UserRepository.class);
        UserService userService = new UserServiceJPA(mockRepository);

        userService.getAllFromPage(1);

        verify(mockRepository, times(1)).getEntryInRange(0, (int) PAGE_SIZE);
    }

    @Test
    public void testGetAllNow() throws Exception {
        UserRepository mockRepository = mock(UserRepository.class);
        UserService userService = new UserServiceJPA(mockRepository);

        userService.getAllNow();

        verify(mockRepository, times(1)).getAll();
    }
}