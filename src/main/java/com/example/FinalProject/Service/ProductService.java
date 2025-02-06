package com.example.FinalProject.Service;

import com.example.FinalProject.GlobalExceptionHandling.GeneralException;
import com.example.FinalProject.Model.ProductModel;
import com.example.FinalProject.Repository.ProductRepository;
import com.example.FinalProject.Request.ProductRequest.AddProductRequest;
import com.example.FinalProject.Response.ProductResponse.AddProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepo;


    public AddProductResponse addProduct(AddProductRequest addRequest)
    {
        ProductModel pm = new ProductModel();
        if(productRepo.getByProductName(addRequest.productName()) != null)
        {
            throw new GeneralException("product already exist");
        }
        pm.setProductName(addRequest.productName());
         productRepo.save(pm);
        return new AddProductResponse(addRequest.productName());
    }

    public void deleteProduct(String id)
    {
        if(productRepo.getById(UUID.fromString(id)) == null)
        {
            throw new GeneralException("id is not exist");
        }
        productRepo.deleteById(UUID.fromString(id));
    }

}
