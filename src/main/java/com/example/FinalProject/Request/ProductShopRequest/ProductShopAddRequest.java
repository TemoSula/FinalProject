package com.example.FinalProject.Request.ProductShopRequest;

import org.springframework.stereotype.Service;


public record ProductShopAddRequest(String shopId, String productId,double productPrice,int productQuantity) {
}
