package com.good.Library.service;

import com.good.Library.entity.User;

public interface UserService {

    public boolean isAdmin(User user);

    public User registration(User user);
}
