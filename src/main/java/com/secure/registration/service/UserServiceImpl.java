package com.secure.registration.service;


import com.secure.registration.dto.UserDTO;
import com.secure.registration.entity.User;
import com.secure.registration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String registerUser(UserDTO userDto) {
        if(userRepository.existsByEmail(userDto .getEmail())){
            return "email already exists";
        }else{
            User user = new User();
            user.setEmail(userDto.getEmail());
            user.setUsername(userDto.getUsername());
            String encodedPassword = passwordEncoder.encode(userDto.getPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);
        }
        return "user registered successfully";
    }
}
