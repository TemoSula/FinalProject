package com.example.FinalProject.Service;

import com.example.FinalProject.Enums.Roles;
import com.example.FinalProject.Enums.UserStatus;
import com.example.FinalProject.GlobalExceptionHandling.GeneralException;
import com.example.FinalProject.Model.*;
import com.example.FinalProject.Repository.PurchaseHistoryRepository;
import com.example.FinalProject.Repository.UserRepository;
import com.example.FinalProject.Request.ProductHistoryRequest.GetProductHistory2;
import com.example.FinalProject.Response.PurchaseHistoryResponse.GetPuchrchaseHistoryResponse;
import com.example.FinalProject.Response.SchedulerRequest.ShopPurchaseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PurchaseHistoryServiceTest {

    @Mock
    PurchaseHistoryRepository phRepo;

    @Mock
    UserRepository userRepo;

    @InjectMocks
    PurchaseHistoryService service;

    @Test
    void getAllPurchaseHistoryWithTimeFiltering() {

        String ShopId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c9a";
        String ProductId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c11";
        String PurchaseHistoryId = "2c4a1022-c5b2-4415-8d48-aa13c33f8c11";
        String ProductShopId = "3e815324-75c9-4b78-9238-f0d70afbedfa";
        String UserId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c93";


        //for Request
        String date = "2025-02-06";

        //ProductModel create object
        ProductModel pm = new ProductModel();
        pm.setId(UUID.fromString(ProductId));
        pm.setProductName("apple");

        //shopModel create Object
        ShopModel sm = new ShopModel();
        sm.setId(UUID.fromString(ShopId));
        sm.setShopName("OriNabiji");
        sm.setShopAddress("Tbillisi");

        //create UserModelObject
        UserModel um = new UserModel();
        um.setId(UUID.fromString(UserId));
        um.setUserName("temo");
        um.setPassword("temotemo123");
        um.setRoles(Roles.USER);
        um.setUserStatus(UserStatus.ACTIVE);


        //create productShopObject
        ProductShop pss = new ProductShop();
        pss.setId(UUID.fromString(ProductShopId));
        pss.setProductModel(pm);
        pss.setProductPrice(1000);
        pss.setQuantity(100);
        pss.setShopModel(new ShopModel());

        //for original object
        PurchaseHistoyModel model = new PurchaseHistoyModel();
        model.setId(UUID.fromString(PurchaseHistoryId));
        model.setTime(new Date(System.currentTimeMillis()));
        model.setProductShop(pss);
        model.setAmount(300);
        model.setQuantity(100);
        model.setUserModel(um);


        List<PurchaseHistoyModel> phm = List.of(model);
//        List<GetPuchrchaseHistoryResponse> phr = List.of(new GetPuchrchaseHistoryResponse
//                (new Date(System.currentTimeMillis()),productName,shopName,AmountPrice,quantity,username));
        when(phRepo.getPuchaseHistory(date)).thenReturn(phm);
        service.getAllPurchaseHistoryWithTimeFiltering(date);
    }

    @Test
    void getPurchaseHistoryWithUserWithTimeFiltering() {

        String date = "2025-02-06";

        String ShopId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c9a";
        String ProductId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c11";
        String PurchaseHistoryId = "2c4a1022-c5b2-4415-8d48-aa13c33f8c11";
        String ProductShopId = "3e815324-75c9-4b78-9238-f0d70afbedfa";
        String UserId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c93";

        ProductModel pm = new ProductModel();
        pm.setId(UUID.fromString(ProductId));
        pm.setProductName("apple");

        //shopModel create Object
        ShopModel sm = new ShopModel();
        sm.setId(UUID.fromString(ShopId));
        sm.setShopName("OriNabiji");
        sm.setShopAddress("Tbillisi");

        //create UserModelObject
        UserModel um = new UserModel();
        um.setId(UUID.fromString(UserId));
        um.setUserName("temo");
        um.setPassword("temotemo123");
        um.setRoles(Roles.USER);
        um.setUserStatus(UserStatus.ACTIVE);


        //create productShopObject
        ProductShop pss = new ProductShop();
        pss.setId(UUID.fromString(ProductShopId));
        pss.setProductModel(pm);
        pss.setProductPrice(1000);
        pss.setQuantity(100);
        pss.setShopModel(new ShopModel());

        //for original object
        PurchaseHistoyModel model = new PurchaseHistoyModel();
        model.setId(UUID.fromString(PurchaseHistoryId));
        model.setTime(new Date(System.currentTimeMillis()));
        model.setProductShop(pss);
        model.setAmount(300);
        model.setQuantity(100);
        model.setUserModel(um);

        when(userRepo.getById(UUID.fromString(UserId))).thenReturn(um);
        List<PurchaseHistoyModel> phm = List.of(model);
        GetProductHistory2 gh2 = new GetProductHistory2(date,UserId);
        when(phRepo.getPurchaseHistoryWithDateAndUserId(date,UUID.fromString(UserId))).thenReturn(phm);
        service.getPurchaseHistoryWithUserWithTimeFiltering(gh2);
        String fakeuserId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c94";
        when(userRepo.getById(UUID.fromString(fakeuserId))).thenReturn(null);
        GetProductHistory2 gh3 = new GetProductHistory2(date,fakeuserId);
        assertThrows(GeneralException.class, () -> {
            service.getPurchaseHistoryWithUserWithTimeFiltering(gh3);
        });
    }

    @Test
    void getAllPuchasedProducts() {

        String ShopId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c9a";
        String ProductId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c11";
        String PurchaseHistoryId = "2c4a1022-c5b2-4415-8d48-aa13c33f8c11";
        String ProductShopId = "3e815324-75c9-4b78-9238-f0d70afbedfa";
        String UserId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c93";

        ProductModel pm = new ProductModel();
        pm.setId(UUID.fromString(ProductId));
        pm.setProductName("apple");

        //shopModel create Object
        ShopModel sm = new ShopModel();
        sm.setId(UUID.fromString(ShopId));
        sm.setShopName("OriNabiji");
        sm.setShopAddress("Tbillisi");

        //create UserModelObject
        UserModel um = new UserModel();
        um.setId(UUID.fromString(UserId));
        um.setUserName("temo");
        um.setPassword("temotemo123");
        um.setRoles(Roles.USER);
        um.setUserStatus(UserStatus.ACTIVE);


        //create productShopObject
        ProductShop pss = new ProductShop();
        pss.setId(UUID.fromString(ProductShopId));
        pss.setProductModel(pm);
        pss.setProductPrice(1000);
        pss.setQuantity(100);
        pss.setShopModel(new ShopModel());

        //for original object
        PurchaseHistoyModel model = new PurchaseHistoyModel();
        model.setId(UUID.fromString(PurchaseHistoryId));
        model.setTime(new Date(System.currentTimeMillis()));
        model.setProductShop(pss);
        model.setAmount(300);
        model.setQuantity(100);
        model.setUserModel(um);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(um.getUserName(),um.getPassword(),
                List.of(new SimpleGrantedAuthority(um.getRoles().toString())));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        List<PurchaseHistoyModel> phm = List.of(model);
        when(phRepo.getAllPurchaseHistoryJustForUserWithoutTimeFiltering(UUID.fromString(UserId))).thenReturn(phm);
        when(userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())).thenReturn(um);
        service.getAllPuchasedProducts();

    }

    @Test
    void getAllPurchaseProduct() {
        String ShopId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c9a";
        String ProductId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c11";
        String PurchaseHistoryId = "2c4a1022-c5b2-4415-8d48-aa13c33f8c11";
        String ProductShopId = "3e815324-75c9-4b78-9238-f0d70afbedfa";
        String UserId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c93";

        ProductModel pm = new ProductModel();
        pm.setId(UUID.fromString(ProductId));
        pm.setProductName("apple");

        //shopModel create Object
        ShopModel sm = new ShopModel();
        sm.setId(UUID.fromString(ShopId));
        sm.setShopName("OriNabiji");
        sm.setShopAddress("Tbillisi");

        //create UserModelObject
        UserModel um = new UserModel();
        um.setId(UUID.fromString(UserId));
        um.setUserName("temo");
        um.setPassword("temotemo123");
        um.setRoles(Roles.USER);
        um.setUserStatus(UserStatus.ACTIVE);


        //create productShopObject
        ProductShop pss = new ProductShop();
        pss.setId(UUID.fromString(ProductShopId));
        pss.setProductModel(pm);
        pss.setProductPrice(1000);
        pss.setQuantity(100);
        pss.setShopModel(new ShopModel());

        //for original object
        PurchaseHistoyModel model = new PurchaseHistoyModel();
        model.setId(UUID.fromString(PurchaseHistoryId));
        model.setTime(new Date(System.currentTimeMillis()));
        model.setProductShop(pss);
        model.setAmount(300);
        model.setQuantity(100);
        model.setUserModel(um);

        List<PurchaseHistoyModel> getAllPurchaseList = List.of(model);
        when(phRepo.findAll()).thenReturn(getAllPurchaseList);
        service.getAllPurchaseProduct();

    }

    @Test
    void sumAllSoldProduct() {
        long totalAmount = 9;
        ShopPurchaseDTO spdto = new ShopPurchaseDTO("Orinabiji",400,totalAmount);

        when(phRepo.getTotalPurchaseAmountByShop()).thenReturn(List.of(spdto));
        service.sumAllSoldProduct();
    }
}