package com.example.FinalProject.Controllers;

import com.example.FinalProject.Request.UserRequest.UserDeactivationRequest;
import com.example.FinalProject.Request.UserRequest.UserLoginRequest;
import com.example.FinalProject.Request.UserRequest.UserRegistrationRequest;
import com.example.FinalProject.Response.UserResponse.UserListResponse;
import com.example.FinalProject.Response.UserResponse.UserRegistrationResponse;
import com.example.FinalProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("User")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/Registration")
    public UserRegistrationResponse Registration(@RequestBody UserRegistrationRequest registrationRequest)
    {
        return userService.Registration(registrationRequest);

    }

    @PostMapping("/Login")
    public String login(@RequestBody UserLoginRequest loginRequest)
    {
       return userService.login(loginRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/SeeAllUsers")
    public List<UserListResponse> allUser()
    {
        return userService.getAllUser();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/DeactivateUser")
    public void DeactiveUser(@RequestBody UserDeactivationRequest Deactive)
    {
        userService.UserDeactivation(Deactive);
    }


}
