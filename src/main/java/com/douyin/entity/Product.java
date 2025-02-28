package com.douyin.entity;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class Product {
    private Long id;
    private String name;
    private String description;
    private double price;
    private Integer stock;  // 库存数量
}
