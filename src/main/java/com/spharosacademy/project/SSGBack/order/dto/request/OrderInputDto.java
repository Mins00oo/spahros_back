package com.spharosacademy.project.SSGBack.order.dto.request;

import com.spharosacademy.project.SSGBack.Cart;
import com.spharosacademy.project.SSGBack.Product;
import com.spharosacademy.project.SSGBack.order.entity.Orders;
import com.spharosacademy.project.SSGBack.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderInputDto { // 가격, 옵션 - 색상, 사이즈, 제품 이름, 수량

    private Long userId;
    private String userAddress;
    private Long orderId;
    private Long price;
    private Long productCnt;
    private String productColor;
    private String productSize;
    private String productName;


    public static OrderInputDto of(Product product, Orders orders, User user){

        return OrderInputDto.builder()
            .userId(user.getUserId())
            .userAddress(user.getUserAddress())
            .orderId(orders.getOrderId())
            .price(product.getPrice())
            .productCnt((long) product.getCnt())
            .productName(product.getName())
            .productSize(product.getSize())
            .productColor(product.getColor())
            .build();
    }

}
