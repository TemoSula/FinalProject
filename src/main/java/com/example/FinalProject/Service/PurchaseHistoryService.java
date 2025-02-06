package com.example.FinalProject.Service;

import com.example.FinalProject.GlobalExceptionHandling.GeneralException;
import com.example.FinalProject.Model.PurchaseHistoyModel;
import com.example.FinalProject.Model.UserModel;
import com.example.FinalProject.Repository.PurchaseHistoryRepository;
import com.example.FinalProject.Repository.UserRepository;
import com.example.FinalProject.Request.ProductHistoryRequest.GetProductHistory2;
import com.example.FinalProject.Response.PurchaseHistoryResponse.GetPuchrchaseHistoryResponse;
import com.example.FinalProject.Response.PurchaseHistoryResponse.UserAndDateGetPuchaseHistoryModelForUser;
import com.example.FinalProject.Response.SchedulerRequest.ShopPurchaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PurchaseHistoryService {


    //ForshopList



    @Autowired
    PurchaseHistoryRepository phRepo;

    @Autowired
    UserRepository UserRepo;


    public List<GetPuchrchaseHistoryResponse> getAllPurchaseHistoryWithTimeFiltering(String date)
    {
        List<GetPuchrchaseHistoryResponse> phr = phRepo.getPuchaseHistory(date).stream().map(x ->
                new GetPuchrchaseHistoryResponse(x.getTime(),x.getProductShop().getProductModel().getProductName(),
                        x.getProductShop().getShopModel().getShopName(), x.getAmount(),x.getQuantity(),x.getUserModel().getUserName())).toList();
        return phr;
    }

    public List<UserAndDateGetPuchaseHistoryModelForUser> getPurchaseHistoryWithUserWithTimeFiltering(GetProductHistory2 getProducthistory2)
    {
        UserModel userModel = UserRepo.getById(UUID.fromString(getProducthistory2.userId()));
        if(userModel == null)
        {
            throw new GeneralException("user is not exsist");
        }

       List<UserAndDateGetPuchaseHistoryModelForUser> uadgphmfu = phRepo.getPurchaseHistoryWithDateAndUserId(getProducthistory2.Date(),userModel.getId())
               .stream().map(x -> new UserAndDateGetPuchaseHistoryModelForUser(x.getProductShop().getProductModel().getProductName(),
                       x.getProductShop().getShopModel().getShopName(),x.getTime(),
                       x.getAmount(),x.getQuantity())).toList();
     return uadgphmfu;
    }

    //without filter any time
    public List<UserAndDateGetPuchaseHistoryModelForUser> getAllPuchasedProducts()
    {
        UserModel userModel = UserRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<UserAndDateGetPuchaseHistoryModelForUser> listforUser = phRepo.getAllPurchaseHistoryJustForUserWithoutTimeFiltering(userModel.getId())
                .stream().map(x -> new UserAndDateGetPuchaseHistoryModelForUser(x.getProductShop().getProductModel().getProductName(),
                        x.getProductShop().getShopModel().getShopName(),x.getTime(),
                        x.getAmount(),x.getQuantity())).toList();
        return listforUser;
    }

    public List<PurchaseHistoyModel> getAllPurchaseProduct()
    {
        return phRepo.findAll();
    }

    @Scheduled(cron = "0 59 23 * * *", zone = "Asia/Tbilisi")
    public void sumAllSoldProduct()
    {
        phRepo.getTotalPurchaseAmountByShop().stream().forEach(x -> System.out.println("ShopName"+ x.shopName() + "||" + "Amount : " + x.TotalAmount() + " || " + "TotalQuantity :" + x.TotalQuantity()));

        //metodi romelic sheinaxavs am monacemebs rodis ra dadasturda
    }



}
