package com.example.FinalProject.Controllers;

import com.example.FinalProject.Request.ProductShopRequest.*;
import com.example.FinalProject.Response.ProductShopResponse.*;
import com.example.FinalProject.Service.ProductShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productShop")
@EnableMethodSecurity
public class ProductShopController {

    @Autowired
    ProductShopService productShopService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/GetAllProductFromShop")
    public List<ProductShopSeeProductsResponse> seeAllProductInShop(@RequestParam String id)
    {
        return productShopService.PspecificShopProducts(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/GetOneProductFromShop")
    public ProductShopSeeProductsResponse SpecificProductsInSpecificShop(@RequestParam String shopId, @RequestParam String productId)
    {
        return productShopService.seeSpecificProductsInSpecificShop(productId,shopId);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/AddProduct")
    public AddProductShopResponse addProducts(@RequestBody AddProductShopRequest addRequest)
    {
     return productShopService.addProductsInTheShop(addRequest);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/editProduct")
    public ShopProductEditResponse editProduct(@RequestBody EditProductShopRequest edit)
    {
        return productShopService.editProductInShop(edit);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/deleteProduct")
    public void deleteProduct(@RequestBody DeleteProductShopRequest deleteRequest)
    {
        productShopService.deleteProductFromShop(deleteRequest);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/BuyProduct")
    public CheckBuyResponse buyProduct(@RequestBody ShopProductBuyProductRequest buyRequest)
    {
        return productShopService.buyProduct(buyRequest);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/fillProductQuantity")
    public ShopProductFillQuantity fillProductQuantity(String shopId, String productId)
    {
        return productShopService.fillQuantity(shopId,productId);
    }
}
