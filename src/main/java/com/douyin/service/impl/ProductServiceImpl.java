package com.douyin.service.impl;

import com.douyin.entity.Product;
import com.douyin.mapper.ProductMapper;
import com.douyin.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product createProduct(Product product) {
        productMapper.insertProduct(product);
        return product;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product existing = productMapper.selectProductById(id);
        if (existing == null) {
            throw new RuntimeException("Product not found");
        }
        product.setId(id);
        productMapper.updateProduct(product);
        return product;
    }

    @Override
    public boolean deleteProduct(Long id) {
        return productMapper.deleteProduct(id) > 0;
    }

    @Override
    public Product getProductById(Long productId) {
        log.info("查询商品: ID={}", productId);
        Product product = productMapper.selectProductById(productId);
        if (product == null) {
            log.error("商品不存在: ID={}", productId);
            throw new RuntimeException("商品不存在: ID=" + productId);
        }
        log.info("商品信息: {}", product);
        return product;
    }

    @Override
    public List<Product> getProductsByIds(List<Long> ids) {
        return productMapper.selectProductsByIds(ids);
    }

    @Override
    public List<Product> getAllProducts() {
        return productMapper.selectAllProducts();
    }
}
