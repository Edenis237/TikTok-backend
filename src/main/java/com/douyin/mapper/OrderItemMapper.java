package com.douyin.mapper;

import com.douyin.entity.OrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderItemMapper {

    @Insert("INSERT INTO order_item(order_id, product_id, quantity, price) VALUES(#{orderId}, #{productId}, #{quantity}, #{price})")
    int insertOrderItem(OrderItem orderItem);

    @Select("SELECT " +
            "id, " +
            "order_id AS orderId, " + // 使用别名
            "product_id AS productId, " + // 关键修复
            "quantity, " +
            "price " +
            "FROM order_item " +
            "WHERE order_id = #{orderId}")
    List<OrderItem> selectItemsByOrderId(Long orderId);
}
