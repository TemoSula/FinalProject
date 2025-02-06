package com.example.FinalProject.Repository;

import com.example.FinalProject.Model.PurchaseHistoyModel;
import com.example.FinalProject.Response.SchedulerRequest.ShopPurchaseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistoyModel, UUID> {

    @Query(value = "SELECT * FROM purchase_history WHERE DATE(time) = CAST(:time AS DATE)", nativeQuery = true)
    List<PurchaseHistoyModel> getPuchaseHistory(@Param("time") String time);

    @Query(value = "SELECT * FROM purchase_history WHERE DATE(time) = CAST(:time AS DATE) and user_id = :userid", nativeQuery = true)
    List<PurchaseHistoyModel> getPurchaseHistoryWithDateAndUserId(@Param("time") String time, @Param("userid") UUID userid);

    @Query(value = "select ph from PurchaseHistoyModel ph where ph.userModel.id  = :userid")
    List<PurchaseHistoyModel> getAllPurchaseHistoryJustForUserWithoutTimeFiltering(@Param("userid") UUID userid);

    @Query("SELECT new com.example.FinalProject.Response.SchedulerRequest.ShopPurchaseDTO(s.shopName, " +
            "SUM(COALESCE(ph.amount, 0)), SUM(COALESCE(ph.quantity, 0))) " +
            "FROM PurchaseHistoyModel ph " +
            "JOIN ProductShop ps ON ph.productShop.id = ps.id " +
            "JOIN ShopModel s ON ps.shopModel.id = s.id " +
            "GROUP BY s.shopName")
    List<ShopPurchaseDTO> getTotalPurchaseAmountByShop();

}
