package cn.iocoder.yudao.module.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class SmsProducer {

    @Resource
    private RocketMQTemplate rocketmqTemplate;


    public void sendMessage() {
        rocketmqTemplate.convertAndSend("test-topic", "Hello, World!");
    }
}
