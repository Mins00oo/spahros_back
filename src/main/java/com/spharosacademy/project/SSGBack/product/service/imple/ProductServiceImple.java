package com.spharosacademy.project.SSGBack.product.service.imple;

import com.spharosacademy.project.SSGBack.product.Image.repository.ProductDetailImgRepository;
import com.spharosacademy.project.SSGBack.product.dto.input.UpdateProductDto;
import com.spharosacademy.project.SSGBack.product.dto.output.ResponseProductDto;
import com.spharosacademy.project.SSGBack.product.entity.Product;
import com.spharosacademy.project.SSGBack.product.dto.input.RequestProductDto;
import com.spharosacademy.project.SSGBack.product.repository.CategorySSRepository;
import com.spharosacademy.project.SSGBack.product.repository.ProductRepository;
import com.spharosacademy.project.SSGBack.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImple implements ProductService {

    private final CategorySSRepository categorySSRepository;
    private final ProductRepository productRepository;
    private final ProductDetailImgRepository productDetailImgRepository;

    @Override
    public Product addProduct(RequestProductDto requestProductDto) {
        return productRepository.save(
                Product.builder()
                        .productName(requestProductDto.getProductName())
                        .price(requestProductDto.getPrice())
                        .productBrand(requestProductDto.getProductBrand())
                        .productColor(requestProductDto.getProductColor())
                        .productCnt(requestProductDto.getProductCnt())
                        .titleImgUrl(requestProductDto.getTitleImgUrl())
                        .categorySS(categorySSRepository.findById(requestProductDto.getCategorySSId()).orElseThrow())
                        .build()
        );
    }

    @Override
    public List<Product> getAll() {
        List<Product> ListProduct = productRepository.findAll();
        return ListProduct;
    }

    @Override
    public ResponseProductDto getProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        product.ifPresent(value -> ResponseProductDto.builder()
                .productId(value.getProductId())
                .productName(value.getProductName())
                .productCnt(value.getProductCnt())
                .productBrand(value.getProductBrand())
                .productColor(value.getProductColor())
                .productDetailImageList(productDetailImgRepository.findAllByproductId(productId))
                .build());
        return null;
    }

    @Override
    public Product editProductById(UpdateProductDto updateProductDto) throws Exception {
        Optional<Product> product = productRepository.findById(updateProductDto.getProductId());
        if (product.isPresent()) {
            productRepository.save(
                    Product.builder()
                            .productId(updateProductDto.getProductId())
                            .productName(updateProductDto.getProductName())
                            .productColor(updateProductDto.getProductColor())
                            .price(updateProductDto.getPrice())
                            .productCnt(updateProductDto.getProductCnt())
                            .productBrand(updateProductDto.getProductBrand())
                            .categorySS(categorySSRepository.findById(updateProductDto.getCategorySSId()).get())
                            .build()
            );
        } else {
            throw new Exception();
        } return null;
    }

    @Override
    public void deleteProductById(Long id) throws Exception {
        Optional<Product> delbyid = productRepository.findById(id);
        if (delbyid.isPresent()) {
            productRepository.deleteById(id);
        } else {
            throw new Exception();
        }
    }
}
