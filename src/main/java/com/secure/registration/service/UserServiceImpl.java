package com.secure.registration.service;


import com.secure.registration.config.SecurityConfig;
import com.secure.registration.dto.UserDTO;
import com.secure.registration.entity.User;
import com.secure.registration.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
}
