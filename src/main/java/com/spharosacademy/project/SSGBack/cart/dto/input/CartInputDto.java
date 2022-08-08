package com.spharosacademy.project.SSGBack.cart.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartInputDto {
    private Long userId;
    private Long productId;
    //productId에 따른 수량 ??
    private int qty;
}
