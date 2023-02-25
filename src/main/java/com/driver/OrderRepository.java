package com.driver;

import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

@Repository
public class OrderRepository {
    HashMap<String, Order> orderMap = new HashMap<>();
    HashMap<String, DeliveryPartner> deliveryPartnerHashMap = new HashMap<>();
    HashMap<String, List<String>> orderToPartnerMap = new HashMap<>();
    HashMap<String, String>  orderAsignMap = new HashMap<>();

    public void addOrder(Order order){
        orderMap.put(order.getId(), order);
    }
    public void addDeliveryPartner(String deliveryPartnerId){
        DeliveryPartner deliveryPartner = new DeliveryPartner(deliveryPartnerId);
        deliveryPartnerHashMap.put(deliveryPartnerId, deliveryPartner);
    }
    public void addOrderPartnerPair(String orderId, String deliveryPartnerId){
        List<String> list = orderToPartnerMap.getOrDefault(deliveryPartnerId, new ArrayList<>());
        list.add(orderId);
        orderToPartnerMap.put(deliveryPartnerId, list);
        orderAsignMap.put(orderId, deliveryPartnerId);
        DeliveryPartner deliveryPartner = deliveryPartnerHashMap.get(deliveryPartnerId);
        deliveryPartner.setNumberOfOrders(list.size());
    }
    public Order getOrderById(String orderId){
        for (String s : orderMap.keySet()){
            if (s.equals(orderId)){
                return orderMap.get(s);
            }
        }
        return null;
    }
    public DeliveryPartner getPartnerById(String deliveryPartnerId){
        if (deliveryPartnerHashMap.containsKey(deliveryPartnerId)){
            return deliveryPartnerHashMap.get(deliveryPartnerId);
        }
        return null;
    }
    public int getOrderCountByPartnerId(String deliveryPartnerId){
        return orderToPartnerMap.getOrDefault(deliveryPartnerId, new ArrayList<>()).size();
    }
    public List<String> getOrderByPartnerId(String deliveryPartnerId){
        return orderToPartnerMap.getOrDefault(deliveryPartnerId, new ArrayList<>());
    }
    public List<String> getAllOrders(){
        List<String> order = new ArrayList<>();
        for (String s : orderMap.keySet()){
            order.add(s);
        }
        return order;
    }
    public int getCountOfUnassignOrders(){
        return orderMap.size() - orderAsignMap.size();
    }
    public int getOrdersLeftAfterGiventimeByPartnerId(String time, String deliveryPartnerId){
        int count = 0;
        List<String> list = orderToPartnerMap.get(deliveryPartnerId);
        int deliveryTime = Integer.parseInt(time.substring(0,2)) * 60 + Integer.parseInt(time.substring(3));
        for (String s : list){
            Order order = orderMap.get(s);
            if(order.getDeliveryTime() > deliveryTime){
                count++;
            }
        }
        return count;
    }
    public String getLastDeliveryTimeByPartnerId(String deliveryPartnerId){
        String time = "";
        List<String> list = orderToPartnerMap.get(deliveryPartnerId);
        int deliveryTime = 0;
        for (String s : list) {
            Order order = orderMap.get(s);
            deliveryTime = Math.max(deliveryTime, order.getDeliveryTime());
        }
        int hour = deliveryTime / 60;
        String delHour = "";
        if (hour < 10) {
            delHour = "0" + String.valueOf(hour);
        } else {
            delHour = String.valueOf(hour);
        }
        int min = deliveryTime % 60;
        String delMin = "";
        if (min < 10) {
            delMin = "0" + String.valueOf(min);
        } else {
            delMin = String.valueOf(min);
        }
        time = delHour + ":" + delMin;
        return time;
    }

    public void deletePartnerById(String deliveryPartnerId){
        deliveryPartnerHashMap.remove(deliveryPartnerId);
        List<String> list = orderToPartnerMap.getOrDefault(deliveryPartnerId, new ArrayList<>());
        ListIterator<String> iterator = list.listIterator();
        while (iterator.hasNext()){
            String s = iterator.next();
            orderAsignMap.remove(s);
        }
        orderToPartnerMap.remove(deliveryPartnerId);
    }
    public void deleteOrderById(String orderId) {
        orderMap.remove(orderId);
        String partnerId = orderAsignMap.get(orderId);
        orderAsignMap.remove(orderId);
        List<String> list = orderToPartnerMap.get(partnerId);
        ListIterator<String> itr = list.listIterator();
        while (itr.hasNext()) {
            String s = itr.next();
            if (s.equals(orderId)) {
                itr.remove();
            }
        }
        orderToPartnerMap.put(partnerId, list);
    }
}
