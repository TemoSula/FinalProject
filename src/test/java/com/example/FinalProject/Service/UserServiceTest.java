package com.example.FinalProject.Service;

import com.example.FinalProject.Enums.Roles;
import com.example.FinalProject.Enums.UserStatus;
import com.example.FinalProject.GlobalExceptionHandling.GeneralException;
import com.example.FinalProject.Model.ProductModel;
import com.example.FinalProject.Model.UserModel;
import com.example.FinalProject.Repository.UserRepository;
import com.example.FinalProject.Request.UserRequest.UserDeactivationRequest;
import com.example.FinalProject.Request.UserRequest.UserLoginRequest;
import com.example.FinalProject.Request.UserRequest.UserRegistrationRequest;
import com.example.FinalProject.Response.UserResponse.UserListResponse;
import com.example.FinalProject.Response.UserResponse.UserRegistrationResponse;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.aggregator.ArgumentAccessException;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.awt.print.Pageable;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepo;
    @InjectMocks
    UserService userService;

    @Mock
    JwtService jwtService;

    @Mock
    BCryptPasswordEncoder encoder;

    @Test
    void registration() {

        String username = "testUser";
        String password = "testPassword";
        //List<String> roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");

        UserRegistrationRequest request = new UserRegistrationRequest(username, password, Roles.USER);

        // Mock the behavior of the encoder (password encoding)
        String encodedPassword = "encodedPassword";
        when(encoder.encode(password)).thenReturn(encodedPassword);

        // Call the registration method
        UserRegistrationResponse response = userService.Registration(request);

        // Verify the repository interaction (save method was called once with the correct UserModel)
        ArgumentCaptor<UserModel> userCaptor = ArgumentCaptor.forClass(UserModel.class);
        verify(userRepo, times(1)).save(userCaptor.capture());

        // Verify that the captured UserModel has the expected values
        UserModel capturedUser = userCaptor.getValue();
        assertEquals(username, capturedUser.getUserName());
        assertEquals(encodedPassword, capturedUser.getPassword());
        assertEquals(Roles.USER, capturedUser.getRoles());
        assertEquals(UserStatus.ACTIVE, capturedUser.getUserStatus());

        // Verify the returned response
        assertNotNull(response);
        assertEquals(username, response.username());
        assertEquals(Roles.USER.toString(), response.Role());

        String username2 = "giorgi";
        UserRegistrationRequest request2 = new UserRegistrationRequest(username2,"password",Roles.USER);
        when(userRepo.findByUsername(username2)).thenReturn(new UserModel());
        assertThrows(GeneralException.class, () ->
        {
            userService.Registration(request2);
        });

    }



        @Test
        void login () {

        String username = "temo";
        String password = "password";
        String ecoddedpassword = "password";

        UserModel userModel = new UserModel();
        userModel.setId(UUID.fromString("2c4a1011-c5b2-4415-8d48-aa13c33f8c93"));
        userModel.setUserName(username);
        userModel.setPassword(ecoddedpassword);
        userModel.setRoles(Roles.USER);
        userModel.setUserStatus(UserStatus.ACTIVE);


        UserLoginRequest login = new UserLoginRequest(username, password);
        when(encoder.matches(password, password)).thenReturn(true);
        when(userRepo.findByUsername(login.username())).thenReturn(userModel);
        when(jwtService.CreateToken(username)).thenReturn("temotemotemo");
        //when(userRepo.findByUsername(login.username())).thenReturn(new UserModel());
        String token = userService.login(login);
        verify(jwtService).CreateToken(login.username());
        ArgumentCaptor<String> userModelArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userRepo).findByUsername(userModelArgumentCaptor.capture());
        UserModel userModel1 = userRepo.findByUsername(userModelArgumentCaptor.getValue());
        assertEquals(userModel.getUserStatus(), UserStatus.ACTIVE);
        assertNotEquals(userModel.getUserStatus(), UserStatus.BLOCKED);
        assertNotNull(token);
        assertEquals("temotemotemo", token);
        assertEquals(userModel1.getUserName(), login.username());
        assertNotNull(userModel1);


        UserModel userModel2 = new UserModel();
        userModel2.setId(UUID.fromString("2c4a1011-c5b2-4415-8d48-aa13c33f8c99"));
        userModel2.setUserName(username);
        userModel2.setPassword(ecoddedpassword);
        userModel2.setRoles(Roles.USER);
        userModel2.setUserStatus(UserStatus.BLOCKED);
        when(userRepo.findByUsername(userModel2.getUserName())).thenReturn(userModel2);
        assertEquals(UserStatus.BLOCKED,userModel2.getUserStatus());
        assertThrows(GeneralException.class, () ->
        {
            userService.login(login);
        });


            String username2 = "giorgi";
            UserLoginRequest request = new UserLoginRequest(username2,"password");
            when(userRepo.findByUsername(username2)).thenReturn(null);
            assertThrows(GeneralException.class, () ->
            {
                userService.login(request);
            });




    }

    @Test
    void getAllUser() {

        String username = "temo";
        String ecoddedpassword = "encoddedpass";
        UserModel userModel = new UserModel();
        userModel.setId(UUID.fromString("2c4a1011-c5b2-4415-8d48-aa13c33f8c93"));
        userModel.setUserName(username);
        userModel.setPassword(ecoddedpassword);
        userModel.setRoles(Roles.USER);
        userModel.setUserStatus(UserStatus.ACTIVE);
        List<UserModel> userList = List.of(userModel);
        when(userRepo.findAll()).thenReturn(userList);
        List<UserListResponse> userList2 = userService.getAllUser();
        verify(userRepo).findAll();
        assertNotNull(userList);
        assertFalse(userList.isEmpty());

        when(userRepo.findAll()).thenReturn(List.of(new UserModel()));
        List<UserListResponse> gag = userService.getAllUser();
        assertNotNull(gag);
        assertEquals("temo", userList2.get(0).username());  // Validate user name mapping
        assertEquals(Roles.USER, userList2.get(0).role());
    }

    @Test
    void userDeactivation() {

        String username = "temo";
        String password = "password";
        String ecoddedpassword = "password";
        String userId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c93";
        UserModel userModel = new UserModel();
        userModel.setId(UUID.fromString("2c4a1011-c5b2-4415-8d48-aa13c33f8c93"));
        userModel.setUserName(username);
        userModel.setPassword(ecoddedpassword);
        userModel.setRoles(Roles.USER);
        userModel.setUserStatus(UserStatus.ACTIVE);
        when(userRepo.getById(UUID.fromString(userId))).thenReturn(new UserModel());

        UserDeactivationRequest deactive = new UserDeactivationRequest(userId,UserStatus.BLOCKED);
        userService.UserDeactivation(deactive);

      ArgumentCaptor<UserModel> userArgument = ArgumentCaptor.forClass(UserModel.class);
       verify(userRepo).save(userArgument.capture());
       UserModel userModel1 = userArgument.getValue();
       assertEquals(UserStatus.BLOCKED,userModel1.getUserStatus());

       when(userRepo.getById(UUID.fromString(userId))).thenReturn(null);
       assertThrows(GeneralException.class, () ->
       {
           userService.UserDeactivation(deactive);
       });

    }
}