package com.store.customer.adapter.out.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class CustomerEventProducer {
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendCustomerMsg(String customermsg) {
        this.kafkaTemplate.send("customer-events", customermsg);
    }
}