package com.spharosacademy.project.SSGBack.product.service.imple;

import com.spharosacademy.project.SSGBack.category.entity.*;
import com.spharosacademy.project.SSGBack.category.exception.CategoryNotFoundException;
import com.spharosacademy.project.SSGBack.category.repository.*;
import com.spharosacademy.project.SSGBack.product.Image.dto.output.OutputDetailImgDto;
import com.spharosacademy.project.SSGBack.product.Image.dto.output.OutputTitleImgDto;
import com.spharosacademy.project.SSGBack.product.Image.entity.ProductDetailImage;
import com.spharosacademy.project.SSGBack.product.Image.entity.ProductTitleImage;
import com.spharosacademy.project.SSGBack.product.Image.repository.ProductDetailImgRepository;
import com.spharosacademy.project.SSGBack.product.Image.repository.ProductTitleImgRepository;
import com.spharosacademy.project.SSGBack.product.dto.input.UpdateProductDto;
import com.spharosacademy.project.SSGBack.product.dto.output.*;
import com.spharosacademy.project.SSGBack.product.entity.Product;
import com.spharosacademy.project.SSGBack.product.dto.input.RequestProductDto;
import com.spharosacademy.project.SSGBack.product.exception.ProductNotFoundException;
import com.spharosacademy.project.SSGBack.product.option.dto.input.OptionInputDto;
import com.spharosacademy.project.SSGBack.product.option.dto.output.OptionOutputDto;
import com.spharosacademy.project.SSGBack.product.option.entity.OptionList;
import com.spharosacademy.project.SSGBack.product.option.repository.OptionRepository;
import com.spharosacademy.project.SSGBack.product.repository.ProductRepository;
import com.spharosacademy.project.SSGBack.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImple implements ProductService {

    private final ProductRepository productRepository;
    private final ProductDetailImgRepository productDetailImgRepository;
    private final CategoryProductListRepository categoryProductListRepository;
    private final CategorySSRepository categorySSRepository;
    private final CategoryMRepository categoryMRepository;
    private final CategorySRepository categorySRepository;
    private final CategoryLRepository categoryLRepository;
    private final ProductTitleImgRepository productTitleImgRepository;
    private final OptionRepository optionRepository;


    @Override
    public Product addProduct(RequestProductDto requestProductDto) {
        Product product = productRepository.save(
                Product.builder()
                        .name(requestProductDto.getName())
                        .sellAmt(requestProductDto.getSellAmount())
                        .priceText(requestProductDto.getPriceText())
                        .mallText(requestProductDto.getMallTxt())
                        .price(requestProductDto.getPrice())
                        .brand(requestProductDto.getBrand())
                        .cnt(requestProductDto.getCnt())
                        .sellAmt(requestProductDto.getSellAmount())
                        .explanation(requestProductDto.getExplanation())
                        .thumbnailUrl(requestProductDto.getThumbnailUrl())
                        .categorySS(categorySSRepository.findById(requestProductDto.getCategorySSId())
                                .orElseThrow(CategoryNotFoundException::new))
                        .build()
        );

        categoryProductListRepository.save(CategoryProductList.builder()
                .categorySS(categorySSRepository.findById(requestProductDto.getCategorySSId())
                        .orElseThrow(ProductNotFoundException::new))
                .categoryS(categorySRepository.findById(requestProductDto.getCategorySId())
                        .orElseThrow(CategoryNotFoundException::new))
                .categoryM(categoryMRepository.findById(requestProductDto.getCategoryMId())
                        .orElseThrow(CategoryNotFoundException::new))
                .categoryL(categoryLRepository.findById(requestProductDto.getCategoryLId())
                        .orElseThrow(CategoryNotFoundException::new))
                .Lname(categoryLRepository.findById(requestProductDto.getCategoryLId())
                        .orElseThrow(CategoryNotFoundException::new).getName())
                .Mname(categoryMRepository.findById(requestProductDto.getCategoryMId())
                        .orElseThrow(CategoryNotFoundException::new).getName())
                .Sname(categorySRepository.findById(requestProductDto.getCategorySId())
                        .orElseThrow(CategoryNotFoundException::new).getName())
                .SSname(categorySSRepository.findById(requestProductDto.getCategorySSId())
                        .orElseThrow(CategoryNotFoundException::new).getName())
                .product(product)
                .build());

        List<OptionInputDto> optionInputDtos = new ArrayList<>();
        for (OptionInputDto optionInputDto : requestProductDto.getOptionInputDtoList()) {
            optionInputDtos.add(OptionInputDto.builder()
                    .color(optionInputDto.getColor())
                    .size(optionInputDto.getSize())
                    .stock(optionInputDto.getStock())
                    .build());
        }

        optionInputDtos.forEach(optionInputDto -> {
            optionRepository.save(
                    OptionList.builder()
                            .color(optionInputDto.getColor())
                            .size(optionInputDto.getSize())
                            .stock(optionInputDto.getStock())
                            .product(product)
                            .build()
            );
        });

        requestProductDto.getInputDetailImgDtoList().forEach(createDetailImgDto -> {
            productDetailImgRepository.save(
                    ProductDetailImage.builder()
                            .productDetailImgUrl(createDetailImgDto.getDetailImgUrl())
                            .productDetailImgTxt(createDetailImgDto.getDetailImgTxt())
                            .product(product)
                            .build()
            );
        });

        requestProductDto.getInputTitleImgDtoList().forEach(createTitleImgDto -> {
            productTitleImgRepository.save(
                    ProductTitleImage.builder()
                            .productTitleImgUrl(createTitleImgDto.getTitleImgUrl())
                            .productTitleImgTxt(createTitleImgDto.getTitleImgTxt())
                            .product(product)
                            .build());
        });

        return product;
    }

    @Override
    public List<OutputSearchProductDto> searchProductByWord(String searchWord, Pageable pageable) {
        List<Product> productList = productRepository.findAllBysearchWord(searchWord, pageable);
        List<OutputSearchProductDto> outputSearchProductDtos = new ArrayList<>();
        if (productList.isEmpty()) {
            System.out.println("검색 결과가 없습니다");
        } else {
            for (Product product : productList) {
                outputSearchProductDtos.add(OutputSearchProductDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .brand(product.getBrand())
                        .mallTxt(product.getMallText())
                        .price(product.getPrice())
                        .thumbnailImgUrl(product.getThumbnailUrl())
                        .priceTxt(product.getPriceText())
                        .build());
            }
        }

        return outputSearchProductDtos;
    }

    @Override
    public List<ResponseProductDto> getAll() {
        List<Product> ListProduct = productRepository.findAll();
        List<ResponseProductDto> responseProductDtoList = new ArrayList<>();

        ListProduct.forEach(product -> {
            List<ProductDetailImage> detailImageList = productDetailImgRepository.findAllByProduct(product);
            List<OutputDetailImgDto> detailDtoList = new ArrayList<>();

            for (ProductDetailImage detailImage : detailImageList) {
                detailDtoList.add(OutputDetailImgDto.builder()
                        .productDetailImgTxt(detailImage.getProductDetailImgTxt())
                        .productDetailImgUrl(detailImage.getProductDetailImgUrl())
                        .build());
            }

            List<OutputTitleImgDto> titleDtoList = new ArrayList<>();
            List<ProductTitleImage> titleImageList = productTitleImgRepository.findAllByProduct(product);

            for (ProductTitleImage productTitleImage : titleImageList) {
                titleDtoList.add(OutputTitleImgDto.builder()
                        .productTitleImgTxt(productTitleImage.getProductTitleImgTxt())
                        .productTitleImgUrl(productTitleImage.getProductTitleImgUrl())
                        .build());
            }

            List<OptionOutputDto> optionOutputDtoList = new ArrayList<>();
            List<OptionList> optionList = optionRepository.findAllByProduct(product);

            for (OptionList option : optionList) {
                optionOutputDtoList.add(OptionOutputDto.builder()
                        .color(option.getColor())
                        .size(option.getSize())
                        .stock(option.getStock())
                        .build());
            }


            List<CategoryProductList> lists = categoryProductListRepository.findAllByProduct(product);
            List<PofCategoryL> categoryLlist = new ArrayList<>();

            for (CategoryProductList categoryProductList : lists) {
                categoryLlist.add(PofCategoryL.builder()
                        .id(categoryProductList.getCategoryL().getId())
                        .name(categoryProductList.getLname())
                        .build());
            }


            List<PofCategoryM> categoryMList = new ArrayList<>();

            for (CategoryProductList categoryProductList : lists) {
                categoryMList.add(PofCategoryM.builder()
                        .id(categoryProductList.getCategoryM().getId())
                        .name(categoryProductList.getMname())
                        .build());
            }

            List<PofCategoryS> categorySList = new ArrayList<>();
            for (CategoryProductList categoryProductList : lists) {
                categorySList.add(PofCategoryS.builder()
                        .id(categoryProductList.getCategoryS().getId())
                        .name(categoryProductList.getSname())
                        .build());
            }

            List<PofCategorySS> categorySSList = new ArrayList<>();
            for (CategoryProductList categoryProductList : lists) {
                categorySSList.add(PofCategorySS.builder()
                        .id(categoryProductList.getCategorySS().getId())
                        .name(categoryProductList.getSSname())
                        .build());
            }

            responseProductDtoList.add(ResponseProductDto.builder()
                    .id(product.getId())
                    .productName(product.getName())
                    .price(product.getPrice())
                    .priceText(product.getPriceText())
                    .mallTxt(product.getMallText())
                    .productBrand(product.getBrand())
                    .productCnt(product.getCnt())
                    .sellAmount(product.getSellAmt())
                    .explanation(product.getExplanation())
                    .thumbnailImgUrl(product.getThumbnailUrl())
                    .pofCategoryLList(categoryLlist)
                    .pofCategoryMList(categoryMList)
                    .pofCategorySList(categorySList)
                    .pofCategorySSList(categorySSList)
                    .outputDetailImgDtos(detailDtoList)
                    .outputTitleImgDtos(titleDtoList)
                    .optionOutputDtos(optionOutputDtoList)
                    .build());
        });
        return responseProductDtoList;
    }

    @Override
    public ResponseRecommendProductDto getRecommendProductById(Long id) {
        Product recproduct = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        return ResponseRecommendProductDto.builder()
                .id(recproduct.getId())
                .name(recproduct.getName())
                .mallText(recproduct.getMallText())
                .brand(recproduct.getBrand())
                .priceText(recproduct.getPriceText())
                .price(recproduct.getPrice())
                .titleImgUrl(recproduct.getThumbnailUrl())
                .build();
    }


    @Override
    public ResponseProductDto getByProductId(Long id) {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        List<ProductDetailImage> detailImageList = productDetailImgRepository.findAllByProduct(product);
        List<OutputDetailImgDto> detailDtoList = new ArrayList<>();

        for (ProductDetailImage detailImage : detailImageList) {
            detailDtoList.add(OutputDetailImgDto.builder()
                    .productDetailImgTxt(detailImage.getProductDetailImgTxt())
                    .productDetailImgUrl(detailImage.getProductDetailImgUrl())
                    .build());
        }

        List<ProductTitleImage> titleImageList = productTitleImgRepository.findAllByProduct(product);
        List<OutputTitleImgDto> titleDtoList = new ArrayList<>();

        for (ProductTitleImage productTitleImage : titleImageList) {
            titleDtoList.add(OutputTitleImgDto.builder()
                    .productTitleImgTxt(productTitleImage.getProductTitleImgTxt())
                    .productTitleImgUrl(productTitleImage.getProductTitleImgUrl())
                    .build());
        }

        List<OptionOutputDto> optionOutputDtoList = new ArrayList<>();
        List<OptionList> optionList = optionRepository.findAllByProduct(product);

        for (OptionList option : optionList) {
            optionOutputDtoList.add(OptionOutputDto.builder()
                    .color(option.getColor())
                    .size(option.getSize())
                    .stock(option.getStock())
                    .build());
        }

        List<CategoryProductList> lists = categoryProductListRepository.findAllByProduct(product);
        List<PofCategoryL> categoryLlist = new ArrayList<>();

        for (CategoryProductList categoryProductList : lists) {
            categoryLlist.add(PofCategoryL.builder()
                    .id(categoryProductList.getCategoryL().getId())
                    .name(categoryProductList.getLname())
                    .build());
        }

        List<PofCategoryM> categoryMList = new ArrayList<>();

        for (CategoryProductList categoryProductList : lists) {
            categoryMList.add(PofCategoryM.builder()
                    .id(categoryProductList.getCategoryM().getId())
                    .name(categoryProductList.getMname())
                    .build());
        }

        List<PofCategoryS> categorySList = new ArrayList<>();
        for (CategoryProductList categoryProductList : lists) {
            categorySList.add(PofCategoryS.builder()
                    .id(categoryProductList.getCategoryS().getId())
                    .name(categoryProductList.getSname())
                    .build());
        }

        List<PofCategorySS> categorySSList = new ArrayList<>();
        for (CategoryProductList categoryProductList : lists) {
            categorySSList.add(PofCategorySS.builder()
                    .id(categoryProductList.getCategorySS().getId())
                    .name(categoryProductList.getSSname())
                    .build());
        }

        return ResponseProductDto.builder()
                .id(product.getId())
                .productName(product.getName())
                .price(product.getPrice())
                .productBrand(product.getBrand())
                .productCnt(product.getCnt())
                .priceText(product.getPriceText())
                .mallTxt(product.getMallText())
                .thumbnailImgUrl(product.getThumbnailUrl())
                .sellAmount(product.getSellAmt())
                .explanation(product.getExplanation())
                .pofCategoryLList(categoryLlist)
                .pofCategoryMList(categoryMList)
                .pofCategorySList(categorySList)
                .pofCategorySSList(categorySSList)
                .outputDetailImgDtos(detailDtoList)
                .outputTitleImgDtos(titleDtoList)
                .optionOutputDtos(optionOutputDtoList)
                .build();
    }

    @Override
    public Product editProductById(UpdateProductDto updateProductDto) {
        Product product = productRepository.findById(updateProductDto.getProductId())
                .orElseThrow(ProductNotFoundException::new);
        productRepository.save(
                Product.builder()
                        .id(updateProductDto.getProductId())
                        .name(updateProductDto.getProductName())
                        .price(updateProductDto.getPrice())
                        .cnt(updateProductDto.getProductCnt())
                        .brand(updateProductDto.getProductBrand())
                        .build()
        );
        return product;
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


