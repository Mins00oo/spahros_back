package com.spharosacademy.project.SSGBack.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //view를 리턴
public class IndexController {

    @GetMapping({"","/"})
    public String index() {
        //머스테치 기본폴더 src/main/resources/
        // 뷰리졸버 설정 : templates ~ 그냥 생략
        return "index";
    }
}
