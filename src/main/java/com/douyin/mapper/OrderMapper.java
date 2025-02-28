package com.douyin.mapper;

import com.douyin.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Insert("INSERT INTO orders(user_id, total_amount, status) VALUES(#{userId}, #{totalAmount}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int createOrder(Order order);

    @Update("UPDATE orders SET status = #{status} WHERE id = #{id}")
    int updateOrderStatus(Long id, String status);

    @Select("SELECT * FROM orders WHERE id = #{orderId} FOR UPDATE")
    Order selectOrderForUpdate(Long orderId);

    @Select("SELECT * FROM orders WHERE user_id = #{userId}")
    List<Order> selectOrdersByUserId(Long userId);
}
