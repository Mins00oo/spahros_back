package com.spharosacademy.project.SSGBack.product.service;

import com.spharosacademy.project.SSGBack.product.dto.input.UpdateProductDto;
import com.spharosacademy.project.SSGBack.product.dto.output.OutputSearchProductDto;
import com.spharosacademy.project.SSGBack.product.dto.output.ResponseProductDto;
import com.spharosacademy.project.SSGBack.product.dto.output.ResponseRecommendProductDto;
import com.spharosacademy.project.SSGBack.product.entity.Product;
import com.spharosacademy.project.SSGBack.product.dto.input.RequestProductDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    Product addProduct(RequestProductDto requestProductDto, MultipartFile multipartFile,
                       List<MultipartFile> multipartFileList, List<MultipartFile> titleFileList) throws IOException;

    List<ResponseProductDto> getAll();

    ResponseProductDto getByProductId(Long id);

    Product editProductById(UpdateProductDto updateProductDto) throws Exception;

    void deleteProductById(Long id) throws Exception;

    ResponseRecommendProductDto getRecommendProductById(Long id);

    List<OutputSearchProductDto> searchProductByWord(String keyword, Pageable pageable);
}
