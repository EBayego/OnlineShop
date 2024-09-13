package com.store.order.adapter.out.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class OrderEventProducer {
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendOrderMsg(String ordermsg) {
        this.kafkaTemplate.send("order-events", ordermsg);
    }
}