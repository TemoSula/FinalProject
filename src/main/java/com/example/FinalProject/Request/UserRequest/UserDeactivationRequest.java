package com.example.FinalProject.Request.UserRequest;

import com.example.FinalProject.Enums.UserStatus;

public record UserDeactivationRequest(String userId, UserStatus userStatus) {
}
