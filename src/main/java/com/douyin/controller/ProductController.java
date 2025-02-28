package com.douyin.controller;

import com.douyin.dto.ProductDTO.CreateProductRequest;
import com.douyin.dto.ProductDTO.UpdateProductRequest;
import com.douyin.entity.Product;
import com.douyin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 创建商品
     */
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody CreateProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        try {
            Product created = productService.createProduct(product);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 修改商品信息
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
                                           @RequestBody UpdateProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        try {
            Product updated = productService.updateProduct(id, product);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 删除商品
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            boolean result = productService.deleteProduct(id);
            if (result) {
                return ResponseEntity.ok("Product deleted successfully");
            } else {
                return ResponseEntity.badRequest().body("Failed to delete product");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 查询单个商品信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            if (product != null) {
                return ResponseEntity.ok(product);
            } else {
                return ResponseEntity.badRequest().body("Product not found");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 批量查询商品信息
     * 如果传入参数 ids，则返回对应的商品列表；否则返回所有商品
     * 例如：GET /products?ids=1,2,3
     */
    @GetMapping
    public ResponseEntity<?> getProducts(@RequestParam(value = "ids", required = false) List<Long> ids) {
        try {
            if (ids != null && !ids.isEmpty()) {
                List<Product> products = productService.getProductsByIds(ids);
                return ResponseEntity.ok(products);
            } else {
                List<Product> products = productService.getAllProducts();
                return ResponseEntity.ok(products);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
