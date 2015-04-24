package ua.epam.rd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.rd.domain.User;
import ua.epam.rd.repository.UserRepository;

import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 24.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
@Service
public class UserServiceJPA implements UserService {
    private static int PAGE_SIZE = 10;

    UserRepository userRepository;

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
}
