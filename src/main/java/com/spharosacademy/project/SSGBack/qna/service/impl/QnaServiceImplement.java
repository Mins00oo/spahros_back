package com.spharosacademy.project.SSGBack.qna.service.impl;

import com.spharosacademy.project.SSGBack.product.entity.Product;
import com.spharosacademy.project.SSGBack.product.repository.ProductRepository;
import com.spharosacademy.project.SSGBack.qna.dto.input.RequestDeleteQnaDto;
import com.spharosacademy.project.SSGBack.qna.dto.input.RequestQnaDto;
import com.spharosacademy.project.SSGBack.qna.dto.input.RequestUpdateQnaDto;
import com.spharosacademy.project.SSGBack.qna.dto.output.ResponseProductQnaDto;
import com.spharosacademy.project.SSGBack.qna.entity.QnA;
import com.spharosacademy.project.SSGBack.qna.repository.QnaRepository;
import com.spharosacademy.project.SSGBack.qna.service.QnaService;
import com.spharosacademy.project.SSGBack.user.domain.User;
import com.spharosacademy.project.SSGBack.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaServiceImplement implements QnaService {

    private final QnaRepository qnaRepository;
    private final ProductRepository productRepository;
    private final IUserRepository iUserRepository;

    @Override
    public void addQna(RequestQnaDto requestQnaDto) {
        User user = iUserRepository.findById(requestQnaDto.getUserId()).get();
        Product product = productRepository.findById(requestQnaDto.getProductId()).get();
        qnaRepository.save(QnA.builder()
                .user(user)
                .product(product)
                .isSecret(requestQnaDto.getIsSecret())
                .qnaContent(requestQnaDto.getQnaContent())
                .qnaTitle(requestQnaDto.getQnaTitle())
                .qnaType(requestQnaDto.getQnaType())
                .build());
    }

    @Override
    public List<ResponseProductQnaDto> getQnaByProductId(Long productId) {
        List<QnA> qnAList = qnaRepository.findAllByProductId(productId);
        List<ResponseProductQnaDto> responseProductQnaDtoList = new ArrayList<>();
        qnAList.forEach(qnA -> {
            responseProductQnaDtoList.add(ResponseProductQnaDto.builder()
                    .productId(qnA.getProduct().getId())
                    .qnaId(qnA.getId())
                    .userLoginId(qnA.getUser().getUserId())
                    .isSecret(qnA.getIsSecret())
                    .qnaContent(qnA.getQnaContent())
                    .qnaTitle(qnA.getQnaTitle())
                    .qnaType(qnA.getQnaType())
                    .regDate(qnA.getCreateDate())
                    .updateDate(qnA.getUpdatedDate())
                    .build());
        });
        return responseProductQnaDtoList;
    }

    @Override
    public QnA editQna(RequestUpdateQnaDto requestUpdateQnaDto) {
        Product product = productRepository.findById(requestUpdateQnaDto.getProductId()).get();
        User user = iUserRepository.findById(requestUpdateQnaDto.getMemberId()).get();
        QnA qnA = qnaRepository.save(QnA.builder()
                .product(product)
                .user(user)
                .qnaType(requestUpdateQnaDto.getQnaType())
                .qnaTitle(requestUpdateQnaDto.getQnaTitle())
                .qnaContent(requestUpdateQnaDto.getQnaContent())
                .isSecret(requestUpdateQnaDto.getIsSecret())
                .id(requestUpdateQnaDto.getQnaId())
                .build());

        return qnA;
    }

    @Override
    public void deleteQna(RequestDeleteQnaDto requestDeleteQnaDto) {
        qnaRepository.deleteById(requestDeleteQnaDto.getQnaId());
    }
}