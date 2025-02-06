package com.example.FinalProject.Service;

import com.example.FinalProject.GlobalExceptionHandling.GeneralException;
import com.example.FinalProject.Model.*;
import com.example.FinalProject.Repository.*;
import com.example.FinalProject.Request.ProductShopRequest.*;
import com.example.FinalProject.Response.ProductShopResponse.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProductShopService {

    @Autowired
    ProductShopRepository psRepo;

    @Autowired
    ProductRepository pr;
    @Autowired
    ShopRepository shopRepository;

    @Autowired
    PurchaseHistoryRepository phr;

    @Autowired
    UserRepository userRepo;
//konkretuli magaziis produqtebis naxva
    public List<ProductShopSeeProductsResponse> PspecificShopProducts(String id)
    {
        ShopModel sm = shopRepository.getById(UUID.fromString(id));
        if(sm == null)
        {
            throw new GeneralException("shop is not exsist");
        }
        List<ProductShopSeeProductsResponse> pm = psRepo.seeProductsWithShopId(UUID.fromString(id)).stream()
                .map(x -> new ProductShopSeeProductsResponse(x.getProductModel().getProductName(),x.getProductPrice(),x.getQuantity())).toList();
        return pm;
    }
    //konkretul mgaziashi konkretuli produqtis naxvebi
    public ProductShopSeeProductsResponse seeSpecificProductsInSpecificShop(String productId, String shopId)
    {
        ShopModel sm = shopRepository.getById(UUID.fromString(shopId));
        if(sm == null){
            throw new GeneralException("shop is not exsist");
        }
        ProductModel pm = pr.getById(UUID.fromString(productId));
        if(pm == null)
        {
            throw new GeneralException("product is not exsist");
        }
        ProductShop ps = psRepo.seeSpecificProdcutWithProductIdAndShopid(UUID.fromString(shopId),UUID.fromString(productId));
        if(ps == null)
        {
            throw  new GeneralException("shop or product is not exsist");
        }
        return new ProductShopSeeProductsResponse(ps.getProductModel().getProductName(),ps.getProductPrice(),ps.getQuantity());
    }
    public AddProductShopResponse addProductsInTheShop(AddProductShopRequest addRequest)
    {
        ShopModel sm = shopRepository.getById(UUID.fromString(addRequest.ShopId()));
        if(sm == null){
            throw new GeneralException("shop is not exsist");
        }
        ProductModel pm = pr.getById(UUID.fromString(addRequest.productId()));
        if(pm == null)
        {
            throw new GeneralException("product is not exsist");
        }
        ProductShop ps = new ProductShop();
        ps.setProductModel(pm);
        ps.setShopModel(sm);
        ps.setProductPrice(addRequest.price());
        ps.setQuantity(addRequest.quantity());
        psRepo.save(ps);
        return new AddProductShopResponse(sm.getShopName(),pm.getProductName(),ps.getProductPrice(),ps.getQuantity());
    }

    public ShopProductEditResponse editProductInShop(EditProductShopRequest editRequest)
    {
        ShopModel sm = shopRepository.getById(UUID.fromString(editRequest.shopId()));
        if(sm == null){
            throw new GeneralException("shop is not exsist");
        }
        ProductModel pm = pr.getById(UUID.fromString(editRequest.productId()));
        if(pm == null)
        {
            throw new GeneralException("product is not exsist");
        }
        ProductShop ps = psRepo.seeSpecificProdcutWithProductIdAndShopid(UUID.fromString(editRequest.shopId()),UUID.fromString(editRequest.productId()));
        if(ps == null)
        {
            throw  new GeneralException("shop or product is not exsist");
        }
        ps.setQuantity(editRequest.productQuantity());
        ps.setProductPrice(editRequest.productPrice());
        psRepo.save(ps);
        return new ShopProductEditResponse(editRequest.productPrice());
    }

    public void deleteProductFromShop(DeleteProductShopRequest deleteRequest)
    {
        ShopModel sm = shopRepository.getById(UUID.fromString(deleteRequest.ShopId()));
        if(sm == null){
            throw new GeneralException("shop is not exsist");
        }
        ProductModel pm = pr.getById(UUID.fromString(deleteRequest.ProductId()));
        if(pm == null)
        {
            throw new GeneralException("product is not exsist");
        }
        ProductShop ps = psRepo.seeSpecificProdcutWithProductIdAndShopid(UUID.fromString(deleteRequest.ShopId()),UUID.fromString(deleteRequest.ProductId()));
        if(ps == null)
        {
            throw  new GeneralException("shop or product is not exsist");
        }
        psRepo.deleteSpecificProdcutWithProductIdAndShopid(UUID.fromString(deleteRequest.ShopId()),UUID.fromString(deleteRequest.ProductId()));

    }

    public CheckBuyResponse buyProduct(ShopProductBuyProductRequest buyRequest)
    {
        ShopModel sm = shopRepository.getById(UUID.fromString(buyRequest.ShopId()));
        if(sm == null){
            throw new GeneralException("shop is not exsist");
        }
        ProductModel pm = pr.getById(UUID.fromString(buyRequest.ProductId()));
        if(pm == null)
        {
            throw new GeneralException("product is not exsist");
        }
        ProductShop ps = psRepo.seeSpecificProdcutWithProductIdAndShopid(UUID.fromString(buyRequest.ShopId()),UUID.fromString(buyRequest.ProductId()));
        if(ps == null)
        {
            throw  new GeneralException("shop or product is not exsist");
        }
        UserModel um = userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if(ps.getQuantity() == 0)
        {
            throw new GeneralException("out of stock");
        }
        if(ps.getQuantity() < buyRequest.quantity())
        {
            throw new GeneralException("to Much we dont have this number of quantity");
        }
        ps.setQuantity(ps.getQuantity() - buyRequest.quantity());
        psRepo.save(ps);
        PurchaseHistoyModel phm = new PurchaseHistoyModel();
        phm.setProductShop(ps);
        phm.setTime(new Date(System.currentTimeMillis()));
        phm.setUserModel(um);
        phm.setAmount(ps.getProductPrice() * buyRequest.quantity());
        phm.setQuantity(buyRequest.quantity());
        phr.save(phm);

        return new CheckBuyResponse(sm.getShopName(),pm.getProductName(),buyRequest.quantity(),ps.getProductPrice() * buyRequest.quantity());
    }

    //romelma iuzerma ra produqtebi iyida,

    //raodenobis shevseba;
    public ShopProductFillQuantity fillQuantity(String shopId, String productId)
    {
        ProductShop productShop = psRepo.seeSpecificProdcutWithProductIdAndShopid(UUID.fromString(shopId), UUID.fromString(productId));
        if(productShop == null)
        {
            throw new GeneralException("product or shop is not exsist");
        }
        if(productShop.getQuantity() == 100)
        {
            throw new GeneralException("quantity is full");
        }
        int justQuantity = 100 - productShop.getQuantity();
        productShop.setQuantity(productShop.getQuantity() + justQuantity);
        psRepo.save(productShop);
        return new ShopProductFillQuantity(productShop.getQuantity());
    }

}
