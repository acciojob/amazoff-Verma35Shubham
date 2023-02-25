package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public void addOrder(Order orderId){
        orderRepository.addOrder(orderId);
    }

    public void addDeliveryPartner(String deliveryPartnerId){
        orderRepository.addDeliveryPartner(deliveryPartnerId);
    }
    public void addOrderPartnerPair(String orderId, String deliveryPartnerId){
        orderRepository.addOrderPartnerPair(orderId, deliveryPartnerId);
    }
    public Order getOrderById(String orderId) {
        Order result = orderRepository.getOrderById(orderId);
        return result;
    }
    public DeliveryPartner getPartnerById(String deliveryPartnerId) {
        DeliveryPartner result = orderRepository.getPartnerById(deliveryPartnerId);
        return result;
    }
    public int getOrderCountByPartnerId(String deliveryPartnerId) {
        int result = orderRepository.getOrderCountByPartnerId(deliveryPartnerId);
        return result;
    }
    public List<String> getOrdersByPartnerId(String deliveryPartnerId) {
        List<String> result = orderRepository.getOrderByPartnerId(deliveryPartnerId);
        return result;
    }

    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public int getCountOfUnassignedOrders() {
        return orderRepository.getCountOfUnassignOrders();
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time, String deliveryPartnerId) {
        return orderRepository.getOrdersLeftAfterGiventimeByPartnerId(time, deliveryPartnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String deliveryPartnerId) {
        return orderRepository.getLastDeliveryTimeByPartnerId(deliveryPartnerId);
    }

    public void deletePartnerById(String deliveryPartnerId) {
        orderRepository.deletePartnerById(deliveryPartnerId);
    }

    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);
    }
}
