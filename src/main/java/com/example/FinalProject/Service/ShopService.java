package com.example.FinalProject.Service;

import com.example.FinalProject.GlobalExceptionHandling.GeneralException;
import com.example.FinalProject.Model.ShopModel;
import com.example.FinalProject.Repository.ShopRepository;
import com.example.FinalProject.Request.ShopRequest.AddShopRequest;
import com.example.FinalProject.Request.ShopRequest.EditShopRequest;
import com.example.FinalProject.Response.ShopResponse.EditShopResponse;
import com.example.FinalProject.Response.ShopResponse.AddShopResponse;
import com.example.FinalProject.Response.ShopResponse.ShopListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ShopService {

    @Autowired
    ShopRepository shopRepo;


    public AddShopResponse addShop(AddShopRequest shopRequest)
    {
        if(shopRepo.getWithShopName(shopRequest.shopName()) != null)
        {
            throw  new GeneralException("shop aready exist");
        }

        ShopModel shopModel = new ShopModel();
        shopModel.setShopName(shopRequest.shopName());
        shopModel.setShopAddress(shopRequest.shopAddress());
        shopRepo.save(shopModel);
        return new AddShopResponse(shopModel.getShopName(),shopModel.getShopAddress());
    }

    public void deleteShop(String id)
    {
        ShopModel sm = shopRepo.getById(UUID.fromString(id));
        if(sm == null)
        {
            throw new GeneralException("shop is not exist");
        }
        shopRepo.deleteById(UUID.fromString(id));
    }

    public EditShopResponse editShop(EditShopRequest editShop)
    {
     ShopModel shopModel = shopRepo.getById(UUID.fromString(editShop.shopId()));
     if(shopModel == null)
     {
         throw new GeneralException("This shop is not exsist");
     }
     shopModel.setShopName(editShop.shopName());
     shopModel.setShopAddress(editShop.shopAddress());
     shopRepo.save(shopModel);
     return new EditShopResponse(editShop.shopName(),editShop.shopAddress());
    }


    public List<ShopListResponse> allShop(int pageNumber, int pageSize)
    {
        Pageable p = PageRequest.of(pageNumber,pageSize);
         List<ShopListResponse> slr = shopRepo.findAll(p).stream().map(x -> new ShopListResponse(x.getShopName(),x.getShopAddress())).toList();
        return slr;
    }


}
