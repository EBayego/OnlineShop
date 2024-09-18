package com.store.product.adapter.out.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class ProductEventProducer {
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendProductMsg(String msg) {
        this.kafkaTemplate.send("product-events", msg);
    }
}