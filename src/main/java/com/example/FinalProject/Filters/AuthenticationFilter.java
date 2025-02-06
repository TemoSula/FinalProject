package com.example.FinalProject.Filters;

import com.example.FinalProject.GlobalExceptionHandling.GeneralException;
import com.example.FinalProject.Model.UserModel;
import com.example.FinalProject.Repository.UserRepository;
import com.example.FinalProject.Service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Payload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Qualifier("MyAuthFilter")
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwt;

    @Autowired
    UserRepository userRepo;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        String token = "";
        if(header == null || !header.startsWith("Bearer "))
        {
            filterChain.doFilter(request,response);
            return;
            //throw custom exeption

            //throw  new RuntimeException("token is not start with bearen or token is null");
        }
        token = header.substring(7);
//        if(jwt.checkExpired(token))
//        {
//            filterChain.doFilter(request,response);
//            throw new GeneralException("token is expired");
//        }
        Claims payload = jwt.GetTokenPayloads(token);
        String username = (String)payload.get("username");
        UserModel userModel = userRepo.findByUsername(username);
        if(SecurityContextHolder.getContext().getAuthentication() == null || SecurityContextHolder.getContext().getAuthentication() != null)
        {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userModel.getUserName(),userModel.getPassword(),
                    List.of(new SimpleGrantedAuthority(userModel.getRoles().toString())));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request,response);
    }
}
