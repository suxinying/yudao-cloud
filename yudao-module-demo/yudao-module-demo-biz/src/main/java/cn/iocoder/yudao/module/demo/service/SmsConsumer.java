package cn.iocoder.yudao.module.demo.service;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@RocketMQMessageListener(
        topic = "test-topic",
        consumerGroup = "demo-server_CONSUMER"
)
public class SmsConsumer implements RocketMQListener<SmsProducer> {
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Override
    public void onMessage(SmsProducer smsProducer) {
        System.out.println("收到消息：" + smsProducer);
    }
}
