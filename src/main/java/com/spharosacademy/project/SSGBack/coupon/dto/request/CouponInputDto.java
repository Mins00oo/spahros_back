package com.spharosacademy.project.SSGBack.coupon.dto.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponInputDto {

    private Long userId;
    private String couponName;
    private Float discountRate;
    private String couponCondition;
    private boolean expiredStatus;


}
