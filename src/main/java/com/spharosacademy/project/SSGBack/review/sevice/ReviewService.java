package com.spharosacademy.project.SSGBack.review.sevice;

import com.spharosacademy.project.SSGBack.review.dto.input.RequestReviewDeleteDto;
import com.spharosacademy.project.SSGBack.review.dto.input.RequestReviewDto;
import com.spharosacademy.project.SSGBack.review.dto.input.RequestUpdateReviewDto;
import com.spharosacademy.project.SSGBack.review.dto.output.ResponseProductReviewDto;
import com.spharosacademy.project.SSGBack.review.dto.output.ResponseUserReviewDto;
import com.spharosacademy.project.SSGBack.review.entity.Review;

import java.util.List;

public interface ReviewService {

    void addReview(RequestReviewDto requestReviewDto);

    void deleteReviewById(RequestReviewDeleteDto requestReviewDeleteDto);

    List<ResponseUserReviewDto> getReviewByUserId(Long userId);

    Review editReviewById(RequestUpdateReviewDto requestUpdateReviewDto);

    List<ResponseProductReviewDto> getAllByProductId(Long productId);
}