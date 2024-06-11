package cn.iocoder.yudao.module.demo.controller;

import cn.iocoder.yudao.module.demo.service.SmsProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;

@RestController
public class ProducerController {
    @Resource
    private SmsProducer smsProducer;

    @GetMapping("/sms")
    @PermitAll
    public String sendSms() {
        smsProducer.sendMessage();
        return "success";
    }
}
