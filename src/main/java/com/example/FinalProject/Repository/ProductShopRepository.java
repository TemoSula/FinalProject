package com.example.FinalProject.Repository;

import com.example.FinalProject.Model.ProductShop;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductShopRepository extends JpaRepository<ProductShop, UUID> {

    @Query ("select ps from ProductShop ps where ps.shopModel.id = :id")
    List<ProductShop> seeProductsWithShopId(@Param("id") UUID id);

    @Query("select ps from ProductShop ps where ps.shopModel.id = :shopId And ps.productModel.id = :productId")
    ProductShop seeSpecificProdcutWithProductIdAndShopid(@Param("shopId") UUID shopId,@Param("productId") UUID productId);

    @Modifying
    @Transactional
    @Query("delete from ProductShop ps where ps.shopModel.id = :shopId And ps.productModel.id = :productId")
    void deleteSpecificProdcutWithProductIdAndShopid(@Param("shopId") UUID shopId,@Param("productId") UUID productId);
}
