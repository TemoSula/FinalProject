package com.example.FinalProject.Service;

import com.example.FinalProject.Enums.Roles;
import com.example.FinalProject.Enums.UserStatus;
import com.example.FinalProject.GlobalExceptionHandling.GeneralException;
import com.example.FinalProject.Model.ProductModel;
import com.example.FinalProject.Model.ProductShop;
import com.example.FinalProject.Model.ShopModel;
import com.example.FinalProject.Model.UserModel;
import com.example.FinalProject.Repository.*;
import com.example.FinalProject.Request.ProductShopRequest.AddProductShopRequest;
import com.example.FinalProject.Request.ProductShopRequest.DeleteProductShopRequest;
import com.example.FinalProject.Request.ProductShopRequest.EditProductShopRequest;
import com.example.FinalProject.Request.ProductShopRequest.ShopProductBuyProductRequest;
import com.example.FinalProject.Response.ProductShopResponse.ProductShopSeeProductsResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductShopServiceTest{

    @Mock
    ProductShopRepository psRepo;

    @Mock
    ProductRepository productRepo;

    @Mock
    ShopRepository shopRepo;

    @Mock
    PurchaseHistoryRepository phRepo;

    @Mock
    UserRepository userRepo;

    @InjectMocks()
    ProductShopService service;


    @Test
    void pspecificShopProducts() {

        String ShopId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c9a";
        when(shopRepo.getById(UUID.fromString(ShopId))).thenReturn(new ShopModel());
        service.PspecificShopProducts(ShopId);
        String shopIdForReturnNull = "2c4a1011-c5b2-4415-8d48-aa13c33f8c10";
        //when(shopRepo.getById(UUID.fromString(shopIdForReturnNull))).thenReturn(null);
        assertThrows(GeneralException.class,() ->
        {
            service.PspecificShopProducts(shopIdForReturnNull);
        });


    }

        @Test
        void seeSpecificProductsInSpecificShop() {
//            //productModel
//            String ShopId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c9a";
//            String ProductId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c11";
//            ProductModel productModel = new ProductModel();
//            productModel.setId(UUID.fromString(ProductId));
//            productModel.setProductName("something is here");
//            String productShopId = "2c4a1011-c5b2-4415-8d48-aa13c33f1111";
//            ProductShop pss = new ProductShop();
//            pss.setId(UUID.fromString(productShopId));
//            pss.setProductModel(productModel);
//            pss.setProductPrice(1000);
//            pss.setQuantity(100);
//            pss.setShopModel(new ShopModel());
//
//            when(shopRepo.getById(UUID.fromString(ShopId))).thenReturn(new ShopModel());
//            when(productRepo.getById(UUID.fromString(ProductId))).thenReturn(productModel);
//            when(psRepo.seeSpecificProdcutWithProductIdAndShopid(UUID.fromString(ShopId), UUID.fromString(ProductId))).thenReturn(pss);
//            ProductShopSeeProductsResponse response = service.seeSpecificProductsInSpecificShop(ProductId, ShopId);
//            assertNotNull(response);
//            String fakeProductId = "2c4a1011-c5b2-4415-8d48-aa13c33f8aaa";
//            // Test 3: Product-Shop combination not found
//            String validShopId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c01";
//            when(psRepo.seeSpecificProdcutWithProductIdAndShopid(UUID.fromString(ShopId), UUID.fromString(ProductId))).thenReturn(null);
//            assertThrows(GeneralException.class, () -> {
//                service.seeSpecificProductsInSpecificShop(fakeProductId, validShopId);
//            });
            String ShopId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c9a";
            String ProductId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c11";

            // Create mock product and ProductShop
            ProductModel productModel = new ProductModel();
            productModel.setId(UUID.fromString(ProductId));
            productModel.setProductName("something is here");

            String productShopId = "2c4a1011-c5b2-4415-8d48-aa13c33f1111";
            ProductShop pss = new ProductShop();
            pss.setId(UUID.fromString(productShopId));
            pss.setProductModel(productModel);
            pss.setProductPrice(1000);
            pss.setQuantity(100);
            pss.setShopModel(new ShopModel());

            // Stubbing shop, product, and product-shop combination
            when(shopRepo.getById(UUID.fromString(ShopId))).thenReturn(new ShopModel());
            when(productRepo.getById(UUID.fromString(ProductId))).thenReturn(productModel);
            when(psRepo.seeSpecificProdcutWithProductIdAndShopid(UUID.fromString(ShopId), UUID.fromString(ProductId))).thenReturn(pss);


            ProductShopSeeProductsResponse response = service.seeSpecificProductsInSpecificShop(ProductId, ShopId);
            assertNotNull(response);

           String fakeShopId = "2c4a1011-c5b2-4415-8d48-aa13c33f8abb";
           when(shopRepo.getById(UUID.fromString(fakeShopId))).thenReturn(null);
            assertThrows(GeneralException.class, () -> {
                service.seeSpecificProductsInSpecificShop(ProductId, fakeShopId);
            });

            // Now test for a case where the product-shop combination does not exist (simulate missing product-shop)
            String fakeProductId = "2c4a1011-c5b2-4415-8d48-aa13c33f8aaa"; // Fake product ID
            when(shopRepo.getById(UUID.fromString(ShopId))).thenReturn(new ShopModel()); // Valid shop
            when(productRepo.getById(UUID.fromString(fakeProductId))).thenReturn(new ProductModel()); // Valid product (fake ID)
            when(psRepo.seeSpecificProdcutWithProductIdAndShopid(UUID.fromString(ShopId), UUID.fromString(fakeProductId))).thenReturn(null); // Product-Shop combo not found

            // Assert that an exception is thrown for missing Product-Shop combination
            assertThrows(GeneralException.class, () -> {
                service.seeSpecificProductsInSpecificShop(fakeProductId, ShopId);
            });



        }




    @Test
    void addProductsInTheShop() {
        String ShopId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c9a";
        String ProductId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c11";
        double price = 100;
        int quantity = 100;
        AddProductShopRequest request = new AddProductShopRequest(ProductId,ShopId,price,quantity);
        when(shopRepo.getById(UUID.fromString(request.ShopId()))).thenReturn(new ShopModel());
        when(productRepo.getById(UUID.fromString(request.productId()))).thenReturn(new ProductModel());
        service.addProductsInTheShop(request);

        String fakeShopId = "2c4a1011-c5b2-4415-8d48-aa13c33f8caa";
        when(shopRepo.getById(UUID.fromString(fakeShopId))).thenReturn(null);
        assertThrows(GeneralException.class,()->
        {
           service.addProductsInTheShop(new AddProductShopRequest(ProductId,fakeShopId,price,quantity));
        });
        String fakeProductId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c22";
        when(productRepo.getById(UUID.fromString(fakeProductId))).thenReturn(null);
        assertThrows(GeneralException.class, () ->
        {
            //rodesac chven gviwevs konkretuli eqsepsheni dahendvla da gvaqvs 3 cali if operatori
            // imisatvis rom shevamowmot monacemebi nulia tua ara pirvel rigshi viwyeb pirveli eqsepsheni
            //shemowmebit da magalitad tu metodis parametrebi mtxovs
            //am shemtxvevashi productis aidis da shopis aidis unda davwero
            //when romelic oriveze daabrunebs obieqts da ara null rac nishnavs rom es aidebi aris validuri
            //amis shemdeg chven gvaqvs magalitad shopis nullze shemowmeba chven methodshi
            //unda gadavcet konkretulad am shemtxvevashi shopid araswori aidi xolo produqtis swori radgan ar moxdes shecdoma
            //rodesac sistema naxavs rom konkretulad es aris ara validuri da produqtis aidi abruenbs produqts da ara null
            //shemowmdeba mxolod shop xolo tuki gvinda produqtis shemowmeba methods gadavcemt
            //validur shop aidis romelic abrunebs shops xolo fake aidi products rom gavigot productma rom daabruna nulli
            service.addProductsInTheShop(new AddProductShopRequest(fakeProductId,ShopId,price,quantity));
        });
    }

    @Test
    void editProductInShop() {
        String ShopId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c9a";
        String ProductId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c11";
        double price = 100;
        int quantity = 100;
        EditProductShopRequest editRequest = new EditProductShopRequest
                (ShopId,ProductId,price,quantity);
        when(shopRepo.getById(UUID.fromString(editRequest.shopId()))).thenReturn(new ShopModel());
        when(productRepo.getById(UUID.fromString(editRequest.productId()))).thenReturn(new ProductModel());
        when(psRepo.seeSpecificProdcutWithProductIdAndShopid(UUID.fromString(editRequest.shopId()),UUID.fromString(editRequest.productId())))
                .thenReturn(new ProductShop());
        service.editProductInShop(editRequest);

        String fakeShopId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c99";
        when(shopRepo.getById(UUID.fromString(fakeShopId))).thenReturn(null);
        assertThrows(GeneralException.class, () ->
        {
            service.editProductInShop(new EditProductShopRequest(fakeShopId,ProductId,price,quantity));
        });

        String fakeProductId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c1d";
        when(productRepo.getById(UUID.fromString(fakeProductId))).thenReturn(null);
        assertThrows(GeneralException.class, () ->
        {
            service.editProductInShop(new EditProductShopRequest(ShopId,fakeProductId,price,quantity));
        });

        when(psRepo.seeSpecificProdcutWithProductIdAndShopid(UUID.fromString(ShopId),UUID.fromString(ProductId))).thenReturn(null);
        assertThrows(GeneralException.class, () ->
        {
            service.editProductInShop(new EditProductShopRequest(ShopId,ProductId,price,quantity));
        });

    }

    @Test
    void deleteProductFromShop() {
        String ShopId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c9a";
        String ProductId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c11";

        when(shopRepo.getById(UUID.fromString(ShopId))).thenReturn(new ShopModel());
        when(productRepo.getById(UUID.fromString(ProductId))).thenReturn(new ProductModel());
        when(psRepo.seeSpecificProdcutWithProductIdAndShopid(UUID.fromString(ShopId),UUID.fromString(ProductId))).thenReturn(new ProductShop());
        service.deleteProductFromShop(new DeleteProductShopRequest(ShopId,ProductId));
        String fakeShopId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c1d";
        when(shopRepo.getById(UUID.fromString(fakeShopId))).thenReturn(null);
        assertThrows(GeneralException.class, () ->
        {
          service.deleteProductFromShop(new DeleteProductShopRequest(fakeShopId,ProductId));
        });
        String fakeProductId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c16";
        when(productRepo.getById(UUID.fromString(fakeProductId))).thenReturn(null);
        assertThrows(GeneralException.class, () ->
        {
            service.deleteProductFromShop(new DeleteProductShopRequest(ShopId,fakeProductId));
        });
        when(psRepo.seeSpecificProdcutWithProductIdAndShopid(UUID.fromString(ShopId),UUID.fromString(ProductId))).thenReturn(null);
        assertThrows(GeneralException.class,() ->
        {
            service.deleteProductFromShop(new DeleteProductShopRequest(ShopId,ProductId));
        });
    }

    @Test
    void buyProduct() {
        String ShopId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c9a";
        String ProductId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c11";
        int quantity = 100;
        ProductShop ps = new ProductShop();
        ps.setId(UUID.fromString("2c4a1011-c5b2-4415-8d48-aa13c33f8c1d"));
        ps.setQuantity(quantity);
        ps.setProductPrice(1000);
        ps.setProductModel(new ProductModel());
        ps.setShopModel(new ShopModel());
        int quantityZeroToCheckException = 0;




        UserModel userModel = new UserModel();
        userModel.setId(UUID.fromString("2c4a1011-c5b2-4415-8d48-aa13c33f8c93"));
        userModel.setUserName("temo");
        userModel.setPassword("ecoddedpassword");
        userModel.setRoles(Roles.USER);
        userModel.setUserStatus(UserStatus.ACTIVE);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userModel.getUserName(),userModel.getPassword(),
                List.of(new SimpleGrantedAuthority(userModel.getRoles().toString())));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        when(shopRepo.getById(UUID.fromString(ShopId))).thenReturn(new ShopModel());
        when(productRepo.getById(UUID.fromString(ProductId))).thenReturn(new ProductModel());
        when(psRepo.seeSpecificProdcutWithProductIdAndShopid(UUID.fromString(ShopId),UUID.fromString(ProductId))).thenReturn(ps);
        service.buyProduct(new ShopProductBuyProductRequest(ShopId,ProductId,2));


        String fakeShopId = "2c4a1012-c5b2-4415-8d48-aa13c33f8c9a";
        when(shopRepo.getById(UUID.fromString(fakeShopId))).thenReturn(null);
        assertThrows(GeneralException.class,() ->
        {
           service.buyProduct(new ShopProductBuyProductRequest(fakeShopId,ProductId,2));
        });
        String fakeProductId = "2c4a1013-c5b2-4415-8d48-aa13c33f8c11";
        when(productRepo.getById(UUID.fromString(fakeProductId))).thenReturn(null);
        assertThrows(GeneralException.class, ()->
        {
            service.buyProduct(new ShopProductBuyProductRequest(ShopId,fakeProductId,2));
        });
        when(psRepo.seeSpecificProdcutWithProductIdAndShopid(UUID.fromString(ShopId),UUID.fromString(ProductId))).thenReturn(null);
        assertThrows(GeneralException.class, () ->
        {
           service.buyProduct(new ShopProductBuyProductRequest(ShopId,ProductId,101));
        });
        //check if product buy quantity is higher product quantity in the shop
        ProductShop ps2 = new ProductShop();
        ps2.setId(UUID.fromString("2c4a1011-c5b2-4415-8d48-aa13c33f8c1d"));
        ps2.setQuantity(30);
        ps2.setProductPrice(1000);
        ps2.setProductModel(new ProductModel());
        ps2.setShopModel(new ShopModel());
        when(psRepo.seeSpecificProdcutWithProductIdAndShopid(UUID.fromString(ShopId),UUID.fromString(ProductId))).thenReturn(ps2);
        assertThrows(GeneralException.class, () ->
        {
            service.buyProduct(new ShopProductBuyProductRequest(ShopId,ProductId,100));
        });

        //chek if exception is throwen when quantity in shop is 0 and we trying to buy 1 or more
        ProductShop ps3 = new ProductShop();
        ps2.setId(UUID.fromString("2c4a1011-c5b2-4415-8d48-aa13c33f8c1d"));
        ps2.setQuantity(0);
        ps2.setProductPrice(1000);
        ps2.setProductModel(new ProductModel());
        ps2.setShopModel(new ShopModel());
        when(psRepo.seeSpecificProdcutWithProductIdAndShopid(UUID.fromString(ShopId),UUID.fromString(ProductId))).thenReturn(ps3);
        assertThrows(GeneralException.class, () ->
        {
            service.buyProduct(new ShopProductBuyProductRequest(ShopId,ProductId,55));
        });

    }

    @Test
    void fillQuantity() {
        String ShopId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c9a";
        String ProductId = "2c4a1011-c5b2-4415-8d48-aa13c33f8c11";

        int quantity = 50;
        ProductShop ps = new ProductShop();
        ps.setId(UUID.fromString("2c4a1011-c5b2-4415-8d48-aa13c33f8c1d"));
        ps.setQuantity(quantity);
        ps.setProductPrice(1000);
        ps.setProductModel(new ProductModel());
        ps.setShopModel(new ShopModel());
        //check if method is working
        when(psRepo.seeSpecificProdcutWithProductIdAndShopid(UUID.fromString(ShopId),UUID.fromString(ProductId))).thenReturn(ps);
        service.fillQuantity(ShopId,ProductId);



        int quantity2= 100;
        ProductShop ps2 = new ProductShop();
        ps2.setId(UUID.fromString("2c4a1011-c5b2-4415-8d48-aa13c33f8c1d"));
        ps2.setQuantity(quantity2);
        ps2.setProductPrice(1000);
        ps2.setProductModel(new ProductModel());
        ps2.setShopModel(new ShopModel());
        //check when quanityt is full how program acting throw or not exception
        when(psRepo.seeSpecificProdcutWithProductIdAndShopid(UUID.fromString(ShopId),UUID.fromString(ProductId))).thenReturn(ps2);
        assertThrows(GeneralException.class,() ->
        {
            service.fillQuantity(ShopId,ProductId);
        });

        //check when if case is return null how program acting throw or not exception
        String fakeShopId = "2c4a1012-c5b2-4415-8d48-aa13c33f8c9a";
        String fakeProductId = "2c4a1013-c5b2-4415-8d48-aa13c33f8c11";
        assertThrows(GeneralException.class,() ->
        {
            service.fillQuantity(fakeShopId,fakeProductId);
        });

    }
}