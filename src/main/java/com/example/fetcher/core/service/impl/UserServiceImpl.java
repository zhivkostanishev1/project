package com.example.fetcher.core.service.impl;

import com.example.fetcher.core.data.User;
import com.example.fetcher.core.data.UserTransactionMapping;
import com.example.fetcher.core.util.JwtUtil;
import com.example.fetcher.database.repository.UserRepository;
import com.example.fetcher.database.repository.UserTransactionRepository;
import com.example.fetcher.core.service.UserService;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.micrometer.common.util.StringUtils.isNotEmpty;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTransactionRepository userTransactionRepository;

    @Override
    public String getJwtTokenIfUserExists(User user) {
        return jwtUtil.generateToken(user);
    }

    @Override
    public List<UserTransactionMapping> getTransactionsForUser(User user) {
        return userTransactionRepository.findByUserId(user.getId());
    }

    @Nullable
    @Override
    public User getUser(String tokenHeader) {
        if (tokenHeader != null) {
            return getUserFromTheDbIfExists(tokenHeader);
        }

        return null;
    }

    private User getUserFromTheDbIfExists(String tokenHeader) {
        User user = jwtUtil.getUserFromToken(tokenHeader);

        if (isNotEmpty(user.getUserName()) && isNotEmpty(user.getPassword())) {
            return userRepository.findByUsernameAndPassword(user.getUserName(), user.getPassword());
        }

        return null;
    }

}
