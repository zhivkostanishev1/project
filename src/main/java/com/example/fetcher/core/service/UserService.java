package com.example.fetcher.core.service;

import com.example.fetcher.core.data.User;
import com.example.fetcher.core.data.UserTransactionMapping;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface UserService {

    public String getJwtTokenIfUserExists(User user);

    public List<UserTransactionMapping> getTransactionsForUser(User user);

    @Nullable
    User getUser(String tokenHeader);
}
