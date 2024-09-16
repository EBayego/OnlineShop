package com.store.customer.adapter.in.messaging;
import java.io.IOException;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class CustomerEventConsumer {

    @KafkaListener(topics = "customer-events", groupId = "customer_group")
    public void consumeCustomerEvent(String customer) throws IOException {
        System.out.println("Recibido evento de cliente: " + customer);
    }
}