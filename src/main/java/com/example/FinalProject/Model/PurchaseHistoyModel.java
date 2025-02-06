package com.example.FinalProject.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "purchase_history")
@Getter
@Setter
public class PurchaseHistoyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Date time;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel userModel;
    @ManyToOne
    @JoinColumn(name = "product_shop_id")
    private ProductShop productShop;
    @Column(name = "amount")
    private double amount;
    @Column(name = "quantity")
    private int quantity;


}
