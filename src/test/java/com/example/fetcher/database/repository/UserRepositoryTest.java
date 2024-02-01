package com.example.fetcher.database.repository;

import com.example.fetcher.FetcherApplication;
import com.example.fetcher.core.data.User;
import com.example.fetcher.database.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FetcherApplication.class)
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveAndFindById_sampleData_resultAsExpected() {
        String userName = "someName";

        User user = userRepository
                .save(User.builder().userName(userName).build());
        User userFound = userRepository
                .findById(user.getId()).orElse(null);

        assertNotNull(userFound);
        assertEquals(user.getUserName(), userFound.getUserName());
        userRepository.deleteAll();
    }

    @Test
    public void saveAllAndFindALlById_sampleData_resultAsExpected() {
        User user1 = User.builder().userName("someUserName").build();
        User user2 = User.builder().userName("anotherUserName").build();
        List<User> users = List.of(user1,user2);

        List<User> usersSaved = userRepository.saveAll(users);
        List<User> usersFound = userRepository
                .findAllById(users.stream().map(User::getId).collect(Collectors.toList()));

        assertNotNull(usersFound);
        assertNotNull(usersSaved);
        assertEquals(usersSaved.size(), usersFound.size());
        assertTrue(usersFound.stream().map(User::getUserName).toList().contains(user2.getUserName()));
        assertTrue(usersFound.stream().map(User::getUserName).toList().contains(user1.getUserName()));
        userRepository.deleteAll();
    }

    @Test
    public void findByUserNameAndPassword_correctData_resultAsExpected() {
        User user = User.builder().userName("someUserName").password("somePassword").build();

        User userSaved = userRepository.save(user);
        User userFound = userRepository.findByUsernameAndPassword(user.getUserName(), user.getPassword());

        assertNotNull(userFound);
        assertNotNull(userSaved);
        assertEquals(user.getUserName(), userFound.getUserName());
        userRepository.deleteAll();
    }

    @Test
    public void findByUserNameAndPassword_wrongUserName_noResultFound() {
        User user = User.builder().userName("someUserName").password("somePassword").build();
        userRepository.save(user);

        User userFound = userRepository.findByUsernameAndPassword("wrongUserName", user.getPassword());

        assertNull(userFound);
        userRepository.deleteAll();
    }

    @Test
    public void findByUserNameAndPassword_wrongPassword_noResultFound() {
        User user = User.builder().userName("someUserName").password("somePassword").build();
        userRepository.save(user);

        User userFound = userRepository.findByUsernameAndPassword(user.getUserName(), "s0mePassword");

        assertNull(userFound);
        userRepository.deleteAll();
    }
}
