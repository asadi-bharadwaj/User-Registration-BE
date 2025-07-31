package com.secure.registration.service;

import com.secure.registration.dto.LoginDTO;
import com.secure.registration.dto.UserDTO;

public interface UserService {

    String registerUser(UserDTO userDto);

    boolean authenticateUser(LoginDTO loginDTO);
}
