package com.example.FinalProject.Configurations;

import com.example.FinalProject.Repository.PurchaseHistoryRepository;
import com.example.FinalProject.Response.SchedulerRequest.ShopPurchaseDTO;
import com.example.FinalProject.Service.PurchaseHistoryService;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class Securityconfig {

    @Autowired
    PurchaseHistoryRepository purchaseRepo;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, @Qualifier("MyAuthFilter")OncePerRequestFilter myAuthFilter) throws Exception {

        return http
                .csrf(customize -> customize.disable())
                .addFilterBefore(myAuthFilter,UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(x -> x.requestMatchers("/User/Login","/User/Registration","/swagger-ui/**",
                        "/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui.html", "/webjars/**").permitAll().anyRequest().authenticated()).build();
    }


    @Bean
    BCryptPasswordEncoder encoder()
    {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info().title("JavaInUse Authentication Service"))
                .addSecurityItem(new SecurityRequirement().addList("JavaInUseSecurityScheme"))
                .components(new Components().addSecuritySchemes("JavaInUseSecurityScheme", new SecurityScheme()
                        .name("JavaInUseSecurityScheme").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));

    }

    /*@Bean
    ApplicationRunner applicationRunner()
    {
        return something ->
        {
            purchaseRepo.getTotalPurchaseAmountByShop().stream().forEach(x -> System.out.println("shopname: " + x.shopName() + " " + "TotalAmount: " +
                    x.TotalAmount() + " " + "totalQuantity: " +  x.TotalQuantity()));

            /*purchaseRepo.getTotalPurchaseAmountByShop().stream().forEach(x
                    -> System.out.println("ShopName"+ x.getShopName() + " || " + "Amount : " + x.getTotalAmount() + " || " + "TotalQuantity: " + x.getTotalQuantity()));
        *//*};*/
    /*}*/

}
