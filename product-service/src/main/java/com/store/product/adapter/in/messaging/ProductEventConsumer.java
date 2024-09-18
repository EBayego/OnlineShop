package com.store.product.adapter.in.messaging;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.product.application.service.ProductService;
import com.store.product.domain.exceptions.InsufficientStockException;

@Component
@Service
public class ProductEventConsumer {

	@Autowired
	private ProductService productService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	// @KafkaListener(topics = "order-events", groupId = "order_group")
	// public void consumeOrderEvent(String order) throws IOException {
	// System.out.println("Recibido evento de pedido: " + order);
	// }

	@KafkaListener(topics = "order-events", groupId = "order_group")
	public void consumeOrderStockEvent(String orderEvent) throws IOException {
		System.out.println("Recibido evento para reducir stock de pedido: " + orderEvent);
		String orderms = orderEvent.stripTrailing();
		orderms = orderEvent.replaceAll("\\\\", "");
		if (orderms.startsWith("\""))
			orderms = orderms.substring(1, orderms.length() - 1);
		System.out.println("Cambiado el string qjitando barras: " + orderms);
		try {
			// Deserializar el JSON recibido en un Map
			Map<String, Object> eventMsg = objectMapper.readValue(orderms, Map.class);

			String eventType = (String) eventMsg.get("eventType");

			if ("reduceStock".equals(eventType)) {
				List<Map<String, Object>> products = (List<Map<String, Object>>) eventMsg.get("products");

				for (Map<String, Object> productData : products) {
					String productId = (String) productData.get("productId");
					Integer quantity = ((Number) productData.get("quantity")).intValue();

					try {
						productService.reduceStock(productId, quantity);
					} catch (InsufficientStockException e) {
						System.err.println("Error: " + e.getMessage());
					}
				}
			} else {
				// Manejar otros tipos de eventos si es necesario
				System.out.println("Evento recibido no es relevante para reducir stock: " + eventType);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error al deserializar el evento: " + e.getMessage());
		}
	}
}