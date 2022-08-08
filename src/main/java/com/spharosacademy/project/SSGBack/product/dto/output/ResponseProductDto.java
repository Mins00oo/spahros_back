package com.spharosacademy.project.SSGBack.product.dto.output;

import com.spharosacademy.project.SSGBack.product.Image.entity.ProductDetailImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseProductDto {

    private Long productId;
    private String productName;
    private int price;
    private String productColor;
    private String productBrand;
    private int productCnt;
    private int CategorySSId;
    private String titleImgUrl;
    private String detailImgUrl;
    private int sellAmount;
    private String explanation;

    List<ProductDetailImage> productDetailImageList;
}
