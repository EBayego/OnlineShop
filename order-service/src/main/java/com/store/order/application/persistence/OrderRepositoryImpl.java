package com.store.order.application.persistence;

import com.store.order.domain.model.Order;
import com.store.order.domain.repository.OrderRepository;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
	
	 @PersistenceContext
	    private EntityManager entityManager;

	    @Override
	    @Transactional
	    public Order save(Order order) {
	        if (order.getId() == null) {
	            entityManager.persist(order);
	            return order;
	        } else {
	            return entityManager.merge(order);
	        }
	    }

	    @Override
	    public Optional<Order> findById(Long id) {
	        Order order = entityManager.find(Order.class, id);
	        return order != null ? Optional.of(order) : Optional.empty();
	    }

	    @Override
	    public List<Order> findAll() {
	        return entityManager.createQuery("SELECT o FROM Order o", Order.class).getResultList();
	    }

	    @Override
	    @Transactional
	    public void deleteById(Long id) {
	        Order order = entityManager.find(Order.class, id);
	        if (order != null) {
	            entityManager.remove(order);
	        }
	    }

	    @Override
	    @Transactional
	    public Order updateStatus(Long id, String status) {
	        Order order = entityManager.find(Order.class, id);
	        if (order != null) {
	            order.setStatus(status);
	            return entityManager.merge(order);
	        }
	        return null;
	    }

}