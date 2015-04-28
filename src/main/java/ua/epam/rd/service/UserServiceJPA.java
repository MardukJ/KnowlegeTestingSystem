package ua.epam.rd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.rd.domain.Token;
import ua.epam.rd.domain.TokenType;
import ua.epam.rd.domain.User;
import ua.epam.rd.repository.GroupRepository;
import ua.epam.rd.repository.UserRepository;
import ua.epam.rd.service.mail.MailComposer;
import ua.epam.rd.service.mail.MailService;

import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 24.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
@Service
public class UserServiceJPA implements UserService {
    public static int PAGE_SIZE = 20;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    @Qualifier("mailService")
    MailService mailService;
    
    //required for mock-tests
    @Autowired
    public UserServiceJPA(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long registerNew(User newUser) {
        if (newUser == null) throw new IllegalArgumentException("nothing to register");

        //Duplicate protection - just return Id if already exist
        User found = userRepository.getByMail(newUser.getEmail());
        if (found != null) return found.getId();

        //New user date verification
        String errorMsg = newUser.verifyMe();
        if (errorMsg != null) {
            throw new IllegalArgumentException(errorMsg);
        }

        //User is new & all fields correct
        return userRepository.add(newUser);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public User checkPasswordAndGetUser(String mail, String password) {
        if ((mail == null) || (password == null))
            throw new IllegalArgumentException("Please enter valid email and password");
        User user = userRepository.getByMail(mail);
        if (user == null) throw new IllegalArgumentException("User not found");
        if (!user.tryPassword(password)) throw new IllegalArgumentException("Invalid password");
        return user;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public int getAllTotalPages() {
        /*
            http://www.baeldung.com/jpa-pagination
        */
        //int pages = (int) ((userRepository.getTotalEntry() / PAGE_SIZE) + 1);
        long totalEntry = userRepository.getTotalEntry();
        int pages = (int) (totalEntry / PAGE_SIZE + (totalEntry % PAGE_SIZE == 0 ? 0 : 1));
        if (pages == 0) pages = 1;
        return pages;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<User> getAllFromPage(int page) {
        return userRepository.getEntryInRange((page - 1) * PAGE_SIZE, PAGE_SIZE);
    }

    @Override
    @Deprecated
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<User> getAllNow() {
        return userRepository.getAll();
    }

    @Override
    public int getAllTotalPagesWFiler(Boolean blocked, Boolean roleTeacher, String regexp) {
        long totalEntry = userRepository.getTotalEntryWithFilter(blocked, roleTeacher, regexp);
        int pages = (int) (totalEntry / PAGE_SIZE + (totalEntry % PAGE_SIZE == 0 ? 0 : 1));
        if (pages == 0) pages = 1;
        return pages;
    }

    @Override
    public List<User> getAllFromPageWFilter(int page, Boolean blocked, Boolean role, Boolean sort, String regexp) {
        return userRepository.getEntryInRangeWithFilter((page - 1) * PAGE_SIZE, PAGE_SIZE, blocked, role, sort, regexp);
    }

    @Override
    //2DO: TEST
    @Transactional(propagation = Propagation.REQUIRED)
    public void sendRestorePasswordToken(String mail) {
        User user = userRepository.getByMail(mail);
        if (user == null) throw new IllegalArgumentException("user not found");
        if (user.getBlocked())
            throw new IllegalArgumentException(user.getEmail() + " is blocked. Please contact your system administrator");
        if (user.getToken() == null) user.setToken(new Token());
        String token = user.getToken().generateToken(TokenType.RESTORE_PASSWORD);
        userRepository.merge(user);
        mailService.sendMailNoConfirmation(user.getEmail(), new MailComposer().RestorePasswordTokenSubject(), new MailComposer().RestorePasswordTokenLetter(token));
    }

    @Override
    //2DO: TEST
    @Transactional(propagation = Propagation.REQUIRED)
    public User validateRestorePasswordToken(String token) {
        User user = userRepository.findUserByToken(token);
        if (user != null) {
            if (!token.equals(user.getToken().getToken())) {
                user = null;
            } else {
                String newPassowrd = user.newRandomPassword();
                user.setToken(null);
                userRepository.merge(user);
                mailService.sendMailNoConfirmation(user.getEmail(), new MailComposer().NewPasswordSubject(), new MailComposer().NewPasswordLetter(newPassowrd));
            }

        }
        return user;
    }

    @Override
    //2DO: Test
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public User getUserInfo(String mail) {
        if (mail == null)
            throw new IllegalArgumentException("Please enter valid email");
        User user = userRepository.getByMail(mail);
        if (user == null) throw new IllegalArgumentException("User not found");
        user.getMembership().size();
        return user;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void blockUser(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Please enter valid id");
        User user = userRepository.getById(id);
        if (user == null) throw new IllegalArgumentException("User not found");
        user.setBlocked(Boolean.TRUE);
        userRepository.merge(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void unblockUser(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Please enter valid id");
        User user = userRepository.getById(id);
        if (user == null) throw new IllegalArgumentException("User not found");
        user.setBlocked(Boolean.FALSE);
        userRepository.merge(user);
    }
}