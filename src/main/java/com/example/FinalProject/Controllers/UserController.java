package com.example.FinalProject.Controllers;

import com.example.FinalProject.Request.UserRequest.UserDeactivationRequest;
import com.example.FinalProject.Request.UserRequest.UserLoginRequest;
import com.example.FinalProject.Request.UserRequest.UserRegistrationRequest;
import com.example.FinalProject.Response.UserResponse.UserListResponse;
import com.example.FinalProject.Response.UserResponse.UserRegistrationResponse;
import com.example.FinalProject.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("User")
@EnableMethodSecurity
@Validated
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/Registration")
    public UserRegistrationResponse Registration(@Valid @RequestBody UserRegistrationRequest registrationRequest)
    {
        return userService.Registration(registrationRequest);

    }

    @PostMapping("/Login")
    public String login(@RequestBody UserLoginRequest loginRequest)
    {
       return userService.login(loginRequest);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/SeeAllUsers")
    public List<UserListResponse> allUser()
    {
        return userService.getAllUser();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/DeactivateUser")
    public void DeactiveUser(@RequestBody UserDeactivationRequest Deactive)
    {
        userService.UserDeactivation(Deactive);
    }


}
