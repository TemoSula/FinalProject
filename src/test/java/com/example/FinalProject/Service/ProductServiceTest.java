package com.example.FinalProject.Service;

import com.example.FinalProject.GlobalExceptionHandling.GeneralException;
import com.example.FinalProject.Model.ProductModel;
import com.example.FinalProject.Model.ShopModel;
import com.example.FinalProject.Repository.ProductRepository;
import com.example.FinalProject.Request.ProductRequest.AddProductRequest;
import com.example.FinalProject.Response.ProductResponse.AddProductResponse;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepo;

    @InjectMocks
    ProductService productService;


//    @Test
//    void addProduct() {
//
//        AddProductResponse apr = new AddProductResponse("Bread");
//
//        AddProductRequest request = new AddProductRequest("Bread");
//
//        ProductModel pm = new ProductModel();
//        pm.setId(UUID.fromString("3e815324-75c9-4b78-9238-f0d70afbedfa"));
//        pm.setProductName("Bread");
//        when(productRepo.save(pm)).thenReturn(pm);
//        when(productService.addProduct(request)).thenReturn(apr);
//        ArgumentCaptor<AddProductRequest> argumentC = ArgumentCaptor.forClass(AddProductRequest.class);
//        ProductModel pm2 = productRepo.save(pm);
//        productService.addProduct(argumentC.capture());
//        Mockito.verify(productRepo).save(pm);
//        Mockito.verify(productService).addProduct(request);
//        assertThat(request).isEqualTo(argumentC.getValue());
//        assertEquals(argumentC.getValue(),request);
//        assertThrows(GeneralException.class, () ->
//        {
//            productRepo.getByProductName("Bread");
//        });
//}

    @BeforeEach
    void initialize()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProduct_whenProductAlreadyExists_shouldThrowException() {
        // Given
        AddProductRequest request = new AddProductRequest("Bread");

        // Mock existing product
        when(productRepo.getByProductName("Bread")).thenReturn(new ProductModel());

        // Then
        assertThrows(GeneralException.class, () -> productService.addProduct(request), "product already exist");
    }





    @Test
    void testAddProduct_whenProductDoesNotExist_shouldSaveProduct() {
        AddProductRequest request = new AddProductRequest("Bread");
        ProductModel pm = new ProductModel();
        pm.setId(UUID.fromString("3e815324-75c9-4b78-9238-f0d70afbedfa"));
        pm.setProductName("Bread");

        // When
        when(productRepo.getByProductName("Bread")).thenReturn(null); // No existing product
        //when(productService.addProduct(request)).thenReturn(new AddProductResponse(request.productName()));
        // Use doReturn().when() to avoid strict stubbing
        doReturn(pm).when(productRepo).save(any(ProductModel.class));

        //ArgumentCaptor<AddProductRequest> ac = ArgumentCaptor.forClass(AddProductRequest.class);
        // Call the method
        AddProductResponse apr = productService.addProduct(request);

        // Then
        ArgumentCaptor<ProductModel> argumentCaptor = ArgumentCaptor.forClass(ProductModel.class);
        verify(productRepo, times(1)).save(argumentCaptor.capture()); // Verify save was called once
        assertEquals("Bread", argumentCaptor.getValue().getProductName()); // Check that the product name is correct

        assertEquals("Bread", apr.productName()); //
    }

    @Test
    void testDelteProduct_withpassId()
    {
//        ProductModel pm = new ProductModel();
//        pm.setId(UUID.fromString("3e815324-75c9-4b78-9238-f0d70afbedfa"));
//        pm.setProductName("Bread");
//        String productid = "8c0ec3bb-06f5-4f14-8422-a86baa4f38ca";
//        productRepo.save(pm);
//        when(productRepo.getById(UUID.fromString(productid))).thenReturn(pm);
//        when(productRepo.getById(UUID.fromString("f5d3d0ea-2415-4a9c-ac27-97678c959e70"))).thenReturn(new ProductModel());
//        productService.deleteProduct(productid);
//        verify(productRepo, times(1)).deleteById(UUID.fromString(productid));
//        assertThrows(GeneralException.class, () ->
//        {
//            productService.deleteProduct("f5d3d0ea-2415-4a9c-ac27-97678c959e70");
//        });



    }

    @Test
    void testThrowExceptionWhenproductisnotexist()
    {
        ProductModel pm = new ProductModel();
        pm.setId(UUID.fromString("3e815324-75c9-4b78-9238-f0d70afbedfa"));
        pm.setProductName("Bread");

        String justfakeID = "8c0ec3bb-06f5-4f14-8422-a86baa4f38ca";
        when(productRepo.getById(UUID.fromString(justfakeID))).thenReturn(null);
        assertThrows(GeneralException.class, () ->
        {
            //productRepo.deleteById(UUID.fromString(justfakeID));
            productService.deleteProduct(justfakeID);
        });
        String justOriginalId = "3e815324-75c9-4b78-9238-f0d70afbedfa";
        when(productRepo.getById(UUID.fromString(justOriginalId))).thenReturn(pm);
        productService.deleteProduct(justOriginalId);
        ArgumentCaptor<UUID> ac = ArgumentCaptor.forClass(UUID.class);
        Mockito.verify(productRepo).deleteById(ac.capture());
        UUID result = ac.getValue();

    }
}