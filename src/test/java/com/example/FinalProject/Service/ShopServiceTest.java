package com.example.FinalProject.Service;

import com.example.FinalProject.GlobalExceptionHandling.GeneralException;
import com.example.FinalProject.Model.ShopModel;
import com.example.FinalProject.Repository.ShopRepository;
import com.example.FinalProject.Request.ShopRequest.AddShopRequest;
import com.example.FinalProject.Request.ShopRequest.EditShopRequest;
import com.example.FinalProject.Response.ShopResponse.ShopListResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShopServiceTest {

    @Mock
    ShopRepository shopRepo;

    @InjectMocks
    ShopService shopService;

    @Test
    void addShop() {

      String realShopName = "magazia";
      String address = "Tbillisi";
      when(shopRepo.getWithShopName(realShopName)).thenReturn(new ShopModel());

      //verify(shopRepo).getWithShopName(fakeShopName);
      assertThrows(GeneralException.class, () ->
      {
          shopService.addShop(new AddShopRequest(realShopName,address));
      });



        String fakeShopName = "magazia";
        String fakeAddress = "Tbillisi";
        AddShopRequest request = new AddShopRequest(fakeShopName,fakeAddress);
      when(shopRepo.getWithShopName(request.shopName())).thenReturn(null);
        shopService.addShop(request);
        /*ArgumentCaptor<ShopModel> um = ArgumentCaptor.forClass(ShopModel.class);
        verify(shopRepo).save(um.capture());
        assertEquals("magazia",um.getValue().getShopName());*/
    }

    @Test
    void deleteShop() {

        String shopId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c93";
        ShopModel shopModel = new ShopModel();
        when(shopRepo.getById(UUID.fromString(shopId))).thenReturn(null/*shopModel*/);
        //shopService.deleteShop(shopId);
        assertThrows(GeneralException.class,() ->
        {
            shopService.deleteShop(shopId);
        });
        when(shopRepo.getById(UUID.fromString(shopId))).thenReturn(shopModel);
        shopService.deleteShop(shopId);
        String shopFakeID = "2c4a1011-c5b2-4415-8d48-aa13c33f8c92";
        when(shopRepo.getById(UUID.fromString(shopFakeID))).thenReturn(null);
        assertThrows(GeneralException.class,() ->
        {
            shopService.deleteShop(shopFakeID);
        });
    }

    @Test
    void editShop() {



        String shopId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c93";
        EditShopRequest request = new EditShopRequest(shopId,"otxinabiji","Tbillisi");
        ShopModel shopModel = new ShopModel();
        shopModel.setId(UUID.fromString(shopId));
        shopModel.setShopName("otxinabiji");
        shopModel.setShopAddress("Tbillisi");
        when(shopRepo.getById(UUID.fromString(shopId))).thenReturn(shopModel);
        when(shopRepo.save(shopModel)).thenReturn(any(shopModel.getClass()));
        ShopModel sm = shopRepo.getById(UUID.fromString(shopId));
        //EditShopResponse response = shopService.editShop(request);
        shopService.editShop(request);
        verify(shopRepo).save(sm);
        assertNotNull(sm);
        //assertNotNull(response);
        String fakeId  = "2c4a1011-c5b2-4415-8d48-aa13c33f8c9a";
        //es daabrunebs am aidit null objects da qvemot editshoprequest ro shevqmnit servisi mag ids amowmebs bazashi da rodesac bazis imitacias vaxdent da aidis varegistrirebt rogor nullis dambrunebeli da am aidis vutitebt mere requestshi is midis da edzebs magas
        when(shopRepo.getById(UUID.fromString(fakeId))).thenReturn(null);
        //EditShopResponse res = shopService.editShop(new EditShopRequest(fakeId,"somet","some"));
        assertThrows(GeneralException.class, () -> {
            shopService.editShop(new EditShopRequest(fakeId,"somet","some"));  // This is the actual method that should throw the exception
        });


    }

    @Test
    void allShop() {
//        ShopModel s1 = new ShopModel();
//        s1.setId(UUID.fromString("2c4a1011-c5b2-4415-8d48-aa13c33f8c9a"));
//        s1.setShopName("magazia");
//        s1.setShopAddress("Tbillisi");
//        ShopModel s2 = new ShopModel();
//        s2.setId(UUID.fromString("2c4a1011-c5b2-4415-8d48-aa13c33f8c10"));
//        s2.setShopName("spari");
//        s2.setShopAddress("gori");
//        int pageNumber = 5;
//        int pageSize = 6;
//        Pageable p = PageRequest.of(pageNumber,pageSize);
//        List<ShopModel> shopList = List.of(s1,s2);
//        Page<ShopModel> page = new PageImpl<>(shopList, (org.springframework.data.domain.Pageable) p,shopList.size());
//        when(shopRepo.findAll(any(Pageable.class))).thenReturn(page);
//        shopService.allShop(pageNumber,pageSize);
        //verify(shopRepo.findAll());

        ShopModel s1 = new ShopModel();
        s1.setId(UUID.fromString("2c4a1011-c5b2-4415-8d48-aa13c33f8c9a"));
        s1.setShopName("magazia");
        s1.setShopAddress("Tbillisi");

        ShopModel s2 = new ShopModel();
        s2.setId(UUID.fromString("2c4a1011-c5b2-4415-8d48-aa13c33f8c10"));
        s2.setShopName("spari");
        s2.setShopAddress("gori");

        // Pagination parameters
        int pageNumber = 5;
        int pageSize = 6;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // Mock repository response
        List<ShopModel> shopList = List.of(s1, s2);
        Page<ShopModel> page = new PageImpl<>(shopList, pageable, shopList.size());

        when(shopRepo.findAll(any(Pageable.class))).thenReturn(page);

        // Call the method under test
        List<ShopListResponse> result = shopService.allShop(pageNumber, pageSize);

        // Assertions
        /*assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("magazia", result.get(0).shopName());
        assertEquals("Tbillisi", result.get(0).shopAddress());
        assertEquals("spari", result.get(1).shopName());
        assertEquals("gori", result.get(1).shopAddress());*/

        // Verify method call
        verify(shopRepo, times(1)).findAll(any(Pageable.class));


    }
}