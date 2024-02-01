package com.example.fetcher.database.repository;

import com.example.fetcher.core.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.userName = :userName AND u.password = :password")
    User findByUsernameAndPassword(@Param("userName") String userName, @Param("password") String password);

}
