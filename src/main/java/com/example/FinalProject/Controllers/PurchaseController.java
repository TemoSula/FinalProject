package com.example.FinalProject.Controllers;

import com.example.FinalProject.Request.ProductHistoryRequest.GetProductHistory2;
import com.example.FinalProject.Response.PurchaseHistoryResponse.GetPuchrchaseHistoryResponse;
import com.example.FinalProject.Response.PurchaseHistoryResponse.UserAndDateGetPuchaseHistoryModelForUser;
import com.example.FinalProject.Service.PurchaseHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
public class PurchaseController {

    @Autowired
    PurchaseHistoryService historyService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllpurchaseHistory")
    public List<GetPuchrchaseHistoryResponse> getAllPurchaseHistory(@RequestParam String date)
    {
        return historyService.getAllPurchaseHistoryWithTimeFiltering(date);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllPuchaseHistoryforAdmin")
    public List<UserAndDateGetPuchaseHistoryModelForUser> getPurchaseHistoryWithUser(@RequestParam String Date,@RequestParam String userId)
    {
        GetProductHistory2 pr = new GetProductHistory2(Date, userId);
        return historyService.getPurchaseHistoryWithUserWithTimeFiltering(pr);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("getMyPurchasedProducts")
    public List<UserAndDateGetPuchaseHistoryModelForUser> getMyProducts(){
        return historyService.getAllPuchasedProducts();
    }



}
