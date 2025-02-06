package com.example.FinalProject.Model;

import com.example.FinalProject.Enums.Roles;
import com.example.FinalProject.Enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;



@Getter
@Setter
@Entity
@Table(name = "users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "username",unique = true)
    private String userName;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Roles roles;
    @Enumerated(value = EnumType.STRING)
    private UserStatus userStatus;

}
