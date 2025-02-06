package com.example.FinalProject.Response.PurchaseHistoryResponse;

import java.util.Date;

public record GetPuchrchaseHistoryResponse(Date time,String productName,String shopName,double AmountPrice,int quantity,String username) {
}
