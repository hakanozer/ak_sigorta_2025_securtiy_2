package com.works.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    @Column(length = 100)
    private String title;
    private Integer price;

}
