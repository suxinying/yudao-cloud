package cn.iocoder.yudao.module.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

@RestController
public class TestController {

    @GetMapping("/test")
    @PermitAll
    public String test() {
        return "test";
    }
}
