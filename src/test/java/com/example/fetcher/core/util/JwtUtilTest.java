package com.example.fetcher.core.util;

import com.example.fetcher.core.data.User;
import com.example.fetcher.core.util.JwtUtil;
import com.example.fetcher.database.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtUtilTest {

    @InjectMocks
    @Spy
    JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(jwtUtil, "expiration", Long.valueOf("8000000"));
        ReflectionTestUtils.setField(jwtUtil, "secret", "YXNkOTgzZTJlMDJlMXJtdjl2dmZ2dzB1bG1if2r3g2asd");
    }

    @Test
    public void getUserFromToken_sampleData_resultAsExpected() {
        User user = User.builder().userName("someName").password("somePassword").build();
        String token = jwtUtil.generateToken(user);

        User userFound = jwtUtil.getUserFromToken(token);

        Assertions.assertEquals(user.getUserName(), userFound.getUserName());
    }

}
