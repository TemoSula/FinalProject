package com.example.FinalProject.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "shop")
@Getter
@Setter
public class ShopModel {
     @Id
     @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
     @Column(name = "shopName", unique = true)
    private String shopName;
    private String shopAddress;

}
