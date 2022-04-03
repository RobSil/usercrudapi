package com.robsil.services;

import com.robsil.models.User;
import com.robsil.repos.UserRepository;
import com.robsil.utils.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;


    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.insert(user);
    }

    public User editUser(User user) {

        boolean isExists = userRepository.existsById(user.getId());

        if (!isExists) {
            throw new UserNotFoundException("There is no user with such ID: " + user.getId());
        }

        return userRepository.save(user);
    }

    public void deleteUserById(String userId) {
        userRepository.deleteById(userId);
    }

}
