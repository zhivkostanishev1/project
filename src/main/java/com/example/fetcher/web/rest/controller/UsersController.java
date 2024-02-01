package com.example.fetcher.web.rest.controller;

import com.example.fetcher.core.data.User;
import com.example.fetcher.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("lime")
public class UsersController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody User user) {
        String token = userService.getJwtTokenIfUserExists(user);

        return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
    }

}
