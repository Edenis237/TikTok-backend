package com.douyin.service.impl;
import lombok.extern.slf4j.Slf4j;
import com.douyin.dto.PaymentResponse;
import com.douyin.entity.Order;
import com.douyin.entity.OrderItem;
import com.douyin.entity.Product;
import com.douyin.mapper.OrderMapper;
import com.douyin.mapper.OrderItemMapper;
import com.douyin.mapper.ProductMapper;
import com.douyin.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Transactional
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ProductMapper productMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PaymentResponse settleOrder(Long orderId,Long userId) {
        Order order = orderMapper.selectOrderForUpdate(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在: ID=" + orderId);
        }

        if (!"pending".equalsIgnoreCase(order.getStatus())) {
            throw new RuntimeException("订单状态无法结算");
        }
        // 实时检查商品库存
        List<OrderItem> items = orderItemMapper.selectItemsByOrderId(orderId);
        log.info("结算订单 {} 的订单项数据：{}", orderId, items); // 关键日志
        for (OrderItem item : items) {
            Product product = productMapper.selectProductById(item.getProductId());
            // 新增非空检查
            if (item.getProductId() == null) {
                log.error("发现异常订单项：{}", item); // 记录完整对象
                throw new RuntimeException("订单项缺少商品ID，订单项ID=" + item.getId());
            }

            if (product == null) {
                throw new RuntimeException("商品不存在: ID=" + item.getProductId());
            }
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("库存不足: " + product.getName());
            }
        }

        // 更新订单状态
        orderMapper.updateOrderStatus(orderId, "settled");

        PaymentResponse response = new PaymentResponse( orderId, order.getTotalAmount(), "settled");
        response.setOrderId(orderId);
        response.setTotalAmount(order.getTotalAmount());
        orderMapper.updateOrderStatus(orderId, "settled");
        return new PaymentResponse(orderId, order.getTotalAmount(), "settled");
    }

    @Override
    public PaymentResponse payOrder(Long orderId, Long userId) {
        // 1. 查询订单并校验归属
        Order order = orderMapper.selectOrderForUpdate(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在: ID=" + orderId);
        }
        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("无权操作他人订单");
        }

        // 2. 校验订单状态是否为 settled
        if (!"settled".equalsIgnoreCase(order.getStatus())) {
            throw new RuntimeException("订单需先完成结算才能支付");
        }

        // 3. 扣减库存并更新状态
        List<OrderItem> items = orderItemMapper.selectItemsByOrderId(orderId);
        for (OrderItem item : items) {
            Product product = productMapper.selectProductById(item.getProductId());
            if (product == null) {
                throw new RuntimeException("商品已下架: ID=" + item.getProductId());
            }
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("库存不足: " + product.getName());
            }
            // 扣减库存
            productMapper.updateProductStock(item.getProductId(), product.getStock() - item.getQuantity());
        }

        order.setStatus("paid");
        orderMapper.updateOrderStatus(orderId, "paid");

        return new PaymentResponse(orderId, order.getTotalAmount(), "paid");
    }


    @Override
    public PaymentResponse cancelPayment(Long orderId, Long userId) {
        // 1. 查询订单并校验归属
        Order order = orderMapper.selectOrderForUpdate(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在: ID=" + orderId);
        }
        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("无权操作他人订单");
        }

        // // 2. 校验订单状态
        // if (!"paid".equalsIgnoreCase(order.getStatus())) {
        //     throw new RuntimeException("仅支持取消已支付订单");
        // }

        // 3. 回退库存和更新状态
        List<OrderItem> items = orderItemMapper.selectItemsByOrderId(orderId);
        for (OrderItem item : items) {
            Product product = productMapper.selectProductById(item.getProductId());
            productMapper.updateProductStock(item.getProductId(), product.getStock() + item.getQuantity());
        }

        order.setStatus("canceled");
        orderMapper.updateOrderStatus(orderId, "canceled");

        return new PaymentResponse( orderId, order.getTotalAmount(), "canceled");
    }
}
