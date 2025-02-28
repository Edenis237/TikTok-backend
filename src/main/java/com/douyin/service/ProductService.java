package com.douyin.service;

import com.douyin.entity.Product;
import java.util.List;

public interface ProductService {
    /**
     * 创建商品
     */
    Product createProduct(Product product);

    /**
     * 修改商品信息
     */
    Product updateProduct(Long id, Product product);

    /**
     * 删除商品
     */
    boolean deleteProduct(Long id);

    /**
     * 查询单个商品信息
     */
    Product getProductById(Long id);

    /**
     * 批量查询商品信息
     */
    List<Product> getProductsByIds(List<Long> ids);

    /**
     * 查询所有商品信息
     */
    List<Product> getAllProducts();
}
