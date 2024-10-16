package com.good.Library.service;

import com.good.Library.exception.UserAlreadyExistException;
import com.good.Library.entity.User;
import com.good.Library.entity.UserRole;
import com.good.Library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    public boolean isAdmin(User user){
        return user.getUserRole() == UserRole.ADMIN;
    }

    public User registration(User user) {
        Optional<User> existingUser=userRepository.findByUserName(user.getUserName());
        if(!existingUser.isPresent()) {
            return userRepository.save(user);
        }
        else{
          throw  new UserAlreadyExistException("User is already registered");
        }
    }

    public Optional<User> getUserByUsername(String userName) {

        return userRepository.findByUserName(userName);
    }

}
