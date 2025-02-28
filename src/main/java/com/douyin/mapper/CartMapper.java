package com.douyin.mapper;

import com.douyin.entity.Cart;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CartMapper {

    @Insert("INSERT INTO cart(user_id) VALUES(#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int createCart(Cart cart);

    @Select("SELECT * FROM cart WHERE user_id = #{userId}")
    Cart selectCartByUserId(Long userId);

    @Delete("DELETE FROM cart WHERE id = #{id}")
    int deleteCartById(Long id);
}
