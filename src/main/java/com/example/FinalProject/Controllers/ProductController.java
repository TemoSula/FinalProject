package com.example.FinalProject.Controllers;

import com.example.FinalProject.Request.ProductRequest.AddProductRequest;
import com.example.FinalProject.Response.ProductResponse.AddProductResponse;
import com.example.FinalProject.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ProductBase")
@EnableMethodSecurity
public class ProductController {

    @Autowired
    ProductService service;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/addProduct")
    public AddProductResponse addProduct(@RequestBody AddProductRequest addProduct)
    {
        return service.addProduct(addProduct);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/deleteProduct")
    public void deleteProduct(@RequestParam String id)
    {
        service.deleteProduct(id);
    }
}
