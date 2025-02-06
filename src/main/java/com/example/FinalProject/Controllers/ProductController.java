package com.example.FinalProject.Controllers;

import com.example.FinalProject.Request.ProductRequest.AddProductRequest;
import com.example.FinalProject.Response.ProductResponse.AddProductResponse;
import com.example.FinalProject.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ProductBase")
public class ProductController {

    @Autowired
    ProductService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addProduct")
    public AddProductResponse addProduct(@RequestBody AddProductRequest addProduct)
    {
        return service.addProduct(addProduct);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteProduct")
    public void deleteProduct(@RequestParam String id)
    {
        service.deleteProduct(id);
    }
}
