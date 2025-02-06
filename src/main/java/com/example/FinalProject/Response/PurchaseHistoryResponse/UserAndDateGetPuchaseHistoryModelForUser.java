package com.example.FinalProject.Response.PurchaseHistoryResponse;

import java.util.Date;

public record UserAndDateGetPuchaseHistoryModelForUser(String ProductName,String shopName,Date time, double amount, int quantity) {
}
