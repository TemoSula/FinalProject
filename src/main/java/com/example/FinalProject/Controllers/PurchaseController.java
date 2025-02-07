package com.example.FinalProject.Controllers;

import com.example.FinalProject.Request.ProductHistoryRequest.GetProductHistory2;
import com.example.FinalProject.Response.PurchaseHistoryResponse.GetPuchrchaseHistoryResponse;
import com.example.FinalProject.Response.PurchaseHistoryResponse.UserAndDateGetPuchaseHistoryModelForUser;
import com.example.FinalProject.Service.PurchaseHistoryService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@EnableMethodSecurity
@Validated
public class PurchaseController {

    @Autowired
    PurchaseHistoryService historyService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAllpurchaseHistory")
    public List<GetPuchrchaseHistoryResponse> getAllPurchaseHistory(
            @RequestParam @Schema(example = "YYYY-MM-DD")@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must follow the format YYYY-MM-DD") String date)
    {
        return historyService.getAllPurchaseHistoryWithTimeFiltering(date);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAllPuchaseHistoryforAdmin")
    public List<UserAndDateGetPuchaseHistoryModelForUser> getPurchaseHistoryWithUser(@RequestParam String Date,@RequestParam String userId)
    {
        GetProductHistory2 pr = new GetProductHistory2(Date, userId);
        return historyService.getPurchaseHistoryWithUserWithTimeFiltering(pr);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("getMyPurchasedProducts")
    public List<UserAndDateGetPuchaseHistoryModelForUser> getMyProducts(){
        return historyService.getAllPuchasedProducts();
    }



}
