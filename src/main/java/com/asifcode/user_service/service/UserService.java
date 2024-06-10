package com.asifcode.user_service.service;

import com.asifcode.user_service.entity.User;
import com.asifcode.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUserById(long id){
        return userRepository.findById(id).get();
    }

    public User findUserByUserName(String userName){
       return userRepository.findByUserName(userName);
    }

    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User registerUser(User user){
        String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        return userRepository.save(user);
    }

    public User updateUser(User updateUser) {


        Long userId = updateUser.getUserId();
        if(userId != null){
        User existingUser = userRepository.findById(userId).get();
            if (updateUser.getUserName() != null) {
                existingUser.setUserName(updateUser.getUserName());
            }
            if (updateUser.getEmail() != null) {
                existingUser.setEmail(updateUser.getEmail());
            }

            if (updateUser.getPassword() != null) {
                existingUser.setPassword(updateUser.getPassword());
            }
            return userRepository.save(existingUser);
        }
        else{

        }
        return null;
    }
}
