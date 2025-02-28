package com.douyin.mapper;

import com.douyin.entity.CartItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartItemMapper {
    @Select("SELECT * FROM cart_item WHERE cart_id = #{cartId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "cartId", column = "cart_id"),
            @Result(property = "product", column = "product_id",
                    one = @One(select = "com.douyin.mapper.ProductMapper.selectProductById")),
            @Result(property = "quantity", column = "quantity")
    })
    List<CartItem> selectItemsByCartId(Long cartId);

    @Insert("INSERT INTO cart_item (cart_id, product_id, quantity) VALUES (#{cartId}, #{product.id}, #{quantity})")
    void addItemToCart(CartItem cartItem);

    @Update("UPDATE cart_item SET quantity = #{quantity} WHERE id = #{id}")
    void updateItemQuantity(CartItem cartItem);

    @Delete("DELETE FROM cart_item WHERE cart_id = #{cartId} AND product_id = #{productId}")
    void removeItemFromCart(@Param("cartId") Long cartId, @Param("productId") Long productId);
}