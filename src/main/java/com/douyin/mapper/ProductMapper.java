package com.douyin.mapper;

import com.douyin.entity.Product;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Insert("INSERT INTO product(name, description, price, stock) VALUES(#{name}, #{description}, #{price}, #{stock})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertProduct(Product product);

    @Update("UPDATE product SET name = #{name}, description = #{description}, price = #{price}, stock = #{stock} WHERE id = #{id}")
    int updateProduct(Product product);

    @Delete("DELETE FROM product WHERE id = #{id}")
    int deleteProduct(Long id);

    // ProductMapper.java
    @Select("SELECT * FROM product WHERE id = #{id}")
    Product selectProductById(Long id);

    /**
     * 更新指定产品的库存
     *
     * @param productId 产品 ID
     * @param newStock  新的库存数量
     * @return 受影响的行数
     */
    @Update("UPDATE product SET stock = #{newStock} WHERE id = #{productId}")
    int updateProductStock(@Param("productId") Long productId, @Param("newStock") int newStock);

    @Lang(XMLLanguageDriver.class)
    @Select("<script>" +
            "SELECT * FROM product WHERE id IN " +
            "<foreach item='id' collection='list' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    List<Product> selectProductsByIds(List<Long> ids);

    @Select("SELECT * FROM product")
    List<Product> selectAllProducts();
}
