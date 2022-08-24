package com.spharosacademy.project.SSGBack.user.controller;

import com.spharosacademy.project.SSGBack.user.dto.response.UserOutputDto;
import com.spharosacademy.project.SSGBack.user.entity.User;
import com.spharosacademy.project.SSGBack.user.service.UserService;
import com.spharosacademy.project.SSGBack.util.JwtTokenProvider;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원정보 조회, 토큰으로 회원조회, memberId 지움
    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public Optional<User> findByUserId() {
        String token = jwtTokenProvider.te();

        Long userId = Long.valueOf(jwtTokenProvider.getUserPk(token));
        return userService.findByUserId(Long.valueOf(jwtTokenProvider.getUserPk(token)));
    }

    // 회원정보 수정, 토큰으로 수정 memberId 지움
    @PutMapping("/modify")
    @ResponseStatus(HttpStatus.OK)
    public void modifyUserInfo(HttpServletRequest request,
                               @RequestBody UserOutputDto userOutputDto) {
        String token = jwtTokenProvider.resolveToken(request);
        userService.modifyUserInfo(Long.valueOf(jwtTokenProvider.getUserPk(token)), userOutputDto);
    }

    // 회원 탈퇴, 삭제 + 토큰으로 삭제하기 memberId 지움
    @DeleteMapping("/remove")
    public User removeUserInfo(HttpServletRequest request,
                               @RequestBody UserOutputDto userOutputDto) {
        String token = jwtTokenProvider.resolveToken(request);

        return userService.removeUserInfo(Long.valueOf(jwtTokenProvider.getUserPk(token)), userOutputDto);
    }

}