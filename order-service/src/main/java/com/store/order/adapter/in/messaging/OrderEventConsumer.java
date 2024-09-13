package com.store.order.adapter.in.messaging;
import java.io.IOException;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class OrderEventConsumer {

    @KafkaListener(topics = "order-events", groupId = "order_group")
    public void consumeOrderEvent(String order) throws IOException {
        System.out.println("Recibido evento de pedido: " + order);
    }
}