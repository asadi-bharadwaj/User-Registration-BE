package com.secure.registration.controller;


import com.secure.registration.dto.LoginDTO;
import com.secure.registration.dto.UserDTO;
import com.secure.registration.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "Hello world";
    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO){
        String result =  userService.registerUser(userDTO);
        return ResponseEntity.ok(result);
    }



    @PostMapping("/login")
    public  ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        boolean result = userService.authenticateUser(loginDTO);
        if(result){
            return ResponseEntity.ok("Login Successfull");
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

}
