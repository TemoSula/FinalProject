package com.example.FinalProject.Controllers;

import com.example.FinalProject.Request.ShopRequest.AddShopRequest;
import com.example.FinalProject.Request.ShopRequest.EditShopRequest;
import com.example.FinalProject.Response.ShopResponse.EditShopResponse;
import com.example.FinalProject.Response.ShopResponse.AddShopResponse;
import com.example.FinalProject.Response.ShopResponse.ShopListResponse;
import com.example.FinalProject.Service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Shop")
@EnableMethodSecurity
public class ShopController {

    @Autowired
    ShopService shopService;
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/addShop")
    public AddShopResponse addShop(@RequestBody AddShopRequest addShop)
    {
        return shopService.addShop(addShop);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("DeleteShop")
    public void deleteShop(@RequestParam String id)
    {
        shopService.deleteShop(id);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/EditShop")
    public EditShopResponse editShop(@RequestBody EditShopRequest editShop)
    {
        return shopService.editShop(editShop);
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/ListOfShops")
    public List<ShopListResponse> ShopList(@RequestParam int pageNumber, @RequestParam int pageSize)
    {
        return shopService.allShop(pageNumber, pageSize);
    }


}

