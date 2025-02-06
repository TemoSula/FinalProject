package com.example.FinalProject.Repository;

import com.example.FinalProject.Model.ShopModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShopRepository extends JpaRepository<ShopModel, UUID> {

    @Modifying
    @Transactional
    @Query("delete from ShopModel sm where sm.id = :deleteId")
    void deleteById(@Param("deleteId") UUID deleteId);

    @Query("select sm from ShopModel sm where sm.id = :selectId")
    ShopModel getById(@Param("selectId") UUID selectId);

    @Query("select sm from ShopModel sm where sm.shopName = :shopName")
    ShopModel getWithShopName(@Param("shopName") String shopName);
}
