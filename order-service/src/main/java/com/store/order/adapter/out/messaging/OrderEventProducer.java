package com.store.order.adapter.out.messaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.order.domain.model.OrderItem;

@Component
@Service
public class OrderEventProducer {
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    //public void sendOrderMsg(String ordermsg) {
    //    this.kafkaTemplate.send("order-events", ordermsg);
    //}
	
	public void sendOrderMsg(String eventType, Long orderId, List<OrderItem> orderItems) {
        Map<String, Object> eventMsg = new HashMap<>();
        eventMsg.put("eventType", eventType);
        eventMsg.put("orderId", orderId);

        List<Map<String, Object>> products = new ArrayList<>();
        for (OrderItem item : orderItems) {
            Map<String, Object> product = new HashMap<>();
            product.put("productId", ""+item.getProductId());
            product.put("quantity", item.getQuantity());
            products.add(product);
        }
        eventMsg.put("products", products);

        try {
            String jsonMessage = objectMapper.writeValueAsString(eventMsg);
            System.out.println("JSON EN TEXTO: " + jsonMessage);

            kafkaTemplate.send("order-events", jsonMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.err.println("Error al convertir el mensaje a JSON: " + e.getMessage());
        }
    }
}