package com.example.FinalProject.Service;

import org.checkerframework.common.value.qual.IntRangeFromGTENegativeOne;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)


class JwtServiceTest {

    @InjectMocks
    JwtService service;

    @Test
    void createToken() {
         String token = service.CreateToken("Temo");

         assertEquals(service.GetTokenPayloads(token).get("username"),"Temo");
         assertFalse(service.checkExpired(token));
    }
}