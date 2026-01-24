package com.idd.usersservice.service;

import com.idd.usersservice.ui.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User createUser(User user);

    User getUser(String userId);

    List<User>getUsers(int page,int limit);
}
