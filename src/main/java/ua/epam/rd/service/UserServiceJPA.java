package ua.epam.rd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Autowired
    UserRepository userRepository;

    @Override
    public Long registerNew(User newUser) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User checkPasswordAndGetUser(String mail, String password) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getAllTotalPages() {
        /*
            http://www.baeldung.com/jpa-pagination
        */
        //int pages = (int) ((userRepository.getTotalEntry() / PAGE_SIZE) + 1);
        long totalEntry = userRepository.getTotalEntry();
        long pages = totalEntry / PAGE_SIZE + (totalEntry % PAGE_SIZE == 0 ? 0 : 1);
        return pages;
    }

    @Override
    public List<User> getAllPage(int page) {
        return userRepository.getEntrysInRange((page - 1) * PAGE_SIZE, PAGE_SIZE);
    }
}
