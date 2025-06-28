package rw.ac.auca.ecommerce.core.order.service;

import rw.ac.auca.ecommerce.core.order.model.Order;
import rw.ac.auca.ecommerce.core.order.model.Order.OrderStatus;
import rw.ac.auca.ecommerce.core.customer.model.Customer;

import java.util.List;
import java.util.UUID;

/**
 * The interface IOrderService.
 *
 * @author Jeremie Ukundwa Tuyisenge
 * @version 1.0
 */
public interface IOrderService {
    Order createOrder(Order theOrder);
    Order updateOrder(Order theOrder);
    Order cancelOrder(Order theOrder);
    Order findOrderByIdAndState(UUID id, Boolean state);
    List<Order> findOrdersByCustomerAndState(Customer customer, Boolean state);
    List<Order> findOrdersByStatusAndState(OrderStatus status, Boolean state);
    List<Order> findOrdersByState(Boolean state);
}