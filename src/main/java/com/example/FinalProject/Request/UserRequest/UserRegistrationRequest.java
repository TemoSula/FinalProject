package com.example.FinalProject.Request.UserRequest;

import com.example.FinalProject.Enums.Roles;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequest(
        @NotNull(message = "username can't be null")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username must contain only Latin letters and numbers.")
        @Schema(example = "Enter Username")
        @Size(min = 4,max = 16,message = "text can't be lower 4 and higher 16")
        String username,

        String password,
        Roles roles)
{
}
