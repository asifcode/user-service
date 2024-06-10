package com.asifcode.user_service.controller;

import com.asifcode.user_service.config.AuthRequest;
import com.asifcode.user_service.entity.User;
import com.asifcode.user_service.service.JwtService;
import com.asifcode.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/users")
@RestController
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("/all")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);

    }

    @GetMapping("username/{userName}")
    public User findUserByUserName(@PathVariable String userName){
        return userService.findUserByUserName(userName);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        if(authenticate.isAuthenticated()){
            String jwtToken = jwtService.generateToken(authRequest.getUserName());
            return ResponseEntity.ok(jwtToken);
        }
        else {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }

    }


    @PostMapping("/register")
    public User registerUser(@RequestBody User user){

        return userService.registerUser(user);
    }

    @PatchMapping("/update")
    public User updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

}
