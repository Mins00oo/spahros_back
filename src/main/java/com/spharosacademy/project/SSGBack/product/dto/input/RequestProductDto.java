package com.spharosacademy.project.SSGBack.product.dto.input;

import com.spharosacademy.project.SSGBack.product.Image.dto.input.InputDetailImgDto;
import com.spharosacademy.project.SSGBack.product.Image.dto.input.InputTitleImgDto;
import com.spharosacademy.project.SSGBack.product.option.dto.input.OptionInputDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Configuration
public class RequestProductDto {

    private String name;
    private String priceText;
    private String brand;
    private int cnt;
    private String explanation;
    private int sellAmount;
    private String mallTxt;
    private int oldPrice;
    private float discountRate;
    private int categorySSId;
    private int categorySId;
    private int categoryMId;
    private int categoryLId;

    List<OptionInputDto> optionInputDtoList;

}
