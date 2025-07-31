package com.secure.registration.service;


import com.secure.registration.config.SecurityConfig;
import com.secure.registration.dto.LoginDTO;
import com.secure.registration.dto.UserDTO;
import com.secure.registration.entity.User;
import com.secure.registration.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public String registerUser(UserDTO userDto) {
        log.info("Registering user with email: {}", userDto.getEmail());
        if(userRepository.existsByEmail(userDto .getEmail())){
            log.info("email already exists: {}", userDto.getEmail());
            return "email already exists";
        }else{
            User user = new User();
            user.setEmail(userDto.getEmail());
            user.setUsername(userDto.getUsername());
            String encodedPassword = passwordEncoder.encode(userDto.getPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);
        }
        log.info("Registering user completed {}", userDto.getEmail());
        return "user registered successfully";
    }

    @Override
    public boolean authenticateUser(LoginDTO loginDTO) {
        log.info("Authenticating user with email: {}", loginDTO.getEmail());

        Optional<User> optionalUser = userRepository.findByEmail(loginDTO.getEmail());

        if (optionalUser.isEmpty()) {
            log.warn("Login failed: email not found - {}", loginDTO.getEmail());
            return false;
        }

        User user = optionalUser.get();
        boolean passwordMatches = passwordEncoder.matches(loginDTO.getPassword(), user.getPassword());
        if (passwordMatches) {
            log.info("User login successful for email: {}", loginDTO.getEmail());
        } else {
            log.warn("User login failed: incorrect password for email: {}", loginDTO.getEmail());
        }

        return passwordMatches;
    }
}
