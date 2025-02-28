package com.douyin.dto.ProductDTO;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class UpdateProductRequest {
    private String name;
    private String description;
    private double price;
    private Integer stock;
}
