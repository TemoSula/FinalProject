package com.example.FinalProject.Repository;

import com.example.FinalProject.Model.ProductModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {


    @Query("Select pm from ProductModel pm where pm.id = :id")
    ProductModel getById(@Param("id") UUID id);

    @Query("Select pm from ProductModel pm where pm.productName = :productName")
    ProductModel getByProductName(@Param("productName") String productName);

    @Modifying
    @Transactional
    @Query("delete from ProductModel pm where pm.id = :deleteId")
    void deleteById(@Param("deleteId") UUID deleteId);


}
