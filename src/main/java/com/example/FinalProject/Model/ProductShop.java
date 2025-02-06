package com.example.FinalProject.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
@Table(name = "product_shop")
@Getter
@Setter

public class ProductShop {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private ShopModel shopModel;
    @ManyToOne
    @JoinColumn(name = "product_id")
    ProductModel productModel;
    private int quantity;
    @Column(name = "productprice")
    private double productPrice;

}
