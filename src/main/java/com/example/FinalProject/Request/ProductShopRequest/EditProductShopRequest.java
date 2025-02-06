package com.example.FinalProject.Request.ProductShopRequest;

public record EditProductShopRequest (String shopId, String productId,Double productPrice, int productQuantity) {
}
