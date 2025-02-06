package com.example.FinalProject.Request.UserRequest;

import com.example.FinalProject.Enums.Roles;

public record UserRegistrationRequest(String username, String password, Roles roles) {
}
