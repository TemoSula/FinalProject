package com.example.FinalProject.Controllers;

import com.example.FinalProject.Request.ShopRequest.AddShopRequest;
import com.example.FinalProject.Request.ShopRequest.EditShopRequest;
import com.example.FinalProject.Response.ShopResponse.EditShopResponse;
import com.example.FinalProject.Response.ShopResponse.AddShopResponse;
import com.example.FinalProject.Response.ShopResponse.ShopListResponse;
import com.example.FinalProject.Service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Shop")
public class ShopController {

    @Autowired
    ShopService shopService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addShop")
    public AddShopResponse addShop(@RequestBody AddShopRequest addShop)
    {
        return shopService.addShop(addShop);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("DeleteShop")
    public void deleteShop(@RequestParam String id)
    {
        shopService.deleteShop(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/EditShop")
    public EditShopResponse editShop(@RequestBody EditShopRequest editShop)
    {
        return shopService.editShop(editShop);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/ListOfShops")
    public List<ShopListResponse> ShopList(@RequestParam int pageNumber, @RequestParam int pageSize)
    {
        return shopService.allShop(pageNumber, pageSize);
    }


}

