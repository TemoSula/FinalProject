package com.example.FinalProject.Service;

import com.example.FinalProject.Enums.UserStatus;
import com.example.FinalProject.GlobalExceptionHandling.GeneralException;
import com.example.FinalProject.Model.UserModel;
import com.example.FinalProject.Repository.UserRepository;
import com.example.FinalProject.Request.UserRequest.UserDeactivationRequest;
import com.example.FinalProject.Request.UserRequest.UserLoginRequest;
import com.example.FinalProject.Request.UserRequest.UserRegistrationRequest;
import com.example.FinalProject.Response.UserResponse.UserRegistrationResponse;
import com.example.FinalProject.Response.UserResponse.UserListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    JwtService jwt;

    @Autowired
    BCryptPasswordEncoder encoder;


    public UserRegistrationResponse Registration(UserRegistrationRequest request)
    {

        UserModel userModel2 = userRepo.findByUsername(request.username());
        if(userModel2 != null)
        {
        throw new GeneralException("user is already exist");
        }

        UserModel userModel = new UserModel();
        userModel.setUserName(request.username());
        userModel.setPassword(encoder.encode(request.password()));
        userModel.setRoles(request.roles());
        userModel.setUserStatus(UserStatus.ACTIVE);
        userRepo.save(userModel);
        return new UserRegistrationResponse(request.username(),request.roles().toString());
    }

    public String login(UserLoginRequest loginRequest)
    {
        UserModel userModel = userRepo.findByUsername(loginRequest.username());
        if (userModel == null || !encoder.matches(loginRequest.password(),userModel.getPassword()))
        {
            throw new GeneralException("username or password is not correct");
        }
        if(userModel.getUserStatus() == UserStatus.BLOCKED)
        {
            throw new GeneralException("user is blocked");
        }

        return jwt.CreateToken(loginRequest.username());

    }

    public List<UserListResponse> getAllUser()
    {

        List<UserListResponse> ulr =  userRepo.findAll().stream().map(x -> new UserListResponse(x.getUserName(),x.getRoles())).toList();
        return ulr;
    }

    public void UserDeactivation(UserDeactivationRequest deactiveRequest)
    {
        UserModel userModel = userRepo.getById(UUID.fromString(deactiveRequest.userId()));
        if(userModel == null)
        {
            throw new GeneralException("user not found");
        }
        userModel.setUserStatus(deactiveRequest.userStatus());
        userRepo.save(userModel);
    }

}
