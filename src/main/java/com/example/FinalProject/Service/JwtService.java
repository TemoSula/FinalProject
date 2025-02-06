package com.example.FinalProject.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Service
public class JwtService {
    private String secretkey;
    public JwtService() throws NoSuchAlgorithmException {
        secretkey = KeyGenerator.getInstance("HMACsha256").generateKey().toString();
    }


    public String CreateToken(String username)
    {
        return Jwts.builder().signWith(Keys.hmacShaKeyFor(secretkey.getBytes()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 90 * 90 * 90))
                .claim("username",username)
                .compact();
    }

    public Claims GetTokenPayloads(String token)
    {
         return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secretkey.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean checkExpired(String token)
    {
        //return true case where token is expired and fales when token is not expired
        return GetTokenPayloads(token).getExpiration().before(new Date(System.currentTimeMillis()));
    }




}
