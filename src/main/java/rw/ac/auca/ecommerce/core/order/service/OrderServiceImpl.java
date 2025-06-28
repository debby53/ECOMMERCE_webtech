package rw.ac.auca.ecommerce.core.order.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.auca.ecommerce.core.order.model.Order;
import rw.ac.auca.ecommerce.core.order.model.Order.OrderStatus;
import rw.ac.auca.ecommerce.core.order.repository.IOrderRepository;
import rw.ac.auca.ecommerce.core.customer.model.Customer;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * The class OrderServiceImpl.
 *
 * @author Jeremie Ukundwa Tuyisenge
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final IOrderRepository orderRepository;

    @Override
    public Order createOrder(Order theOrder) {
        return orderRepository.save(theOrder);
    }

    @Override
    public Order updateOrder(Order theOrder) {
        Order found = findOrderByIdAndState(theOrder.getId(), Boolean.TRUE);
        if (Objects.nonNull(found)) {
            found.setCustomer(theOrder.getCustomer());
            found.setOrderDate(theOrder.getOrderDate());
            found.setTotalAmount(theOrder.getTotalAmount());
            found.setStatus(theOrder.getStatus());
            found.setOrderItems(theOrder.getOrderItems());
            return orderRepository.save(found);
        }
        throw new ObjectNotFoundException(Order.class, "Order Object not found");
    }

    @Override
    public Order cancelOrder(Order theOrder) {
        Order found = findOrderByIdAndState(theOrder.getId(), Boolean.TRUE);
        if (Objects.nonNull(found)) {
            found.setStatus(OrderStatus.CANCELLED);
            found.setActive(Boolean.FALSE);
            return orderRepository.save(found);
        }
        throw new ObjectNotFoundException(Order.class, "Order Object not found");
    }

    @Override
    public Order findOrderByIdAndState(UUID id, Boolean state) {
        Order theOrder = orderRepository.findByIdAndActive(id, state)
                .orElseThrow(() -> new ObjectNotFoundException(Order.class, "Order not found"));
        return theOrder;
    }

    @Override
    public List<Order> findOrdersByCustomerAndState(Customer customer, Boolean state) {
        return orderRepository.findAllByCustomerAndActive(customer, state);
    }

    @Override
    public List<Order> findOrdersByStatusAndState(OrderStatus status, Boolean state) {
        return orderRepository.findAllByStatusAndActive(status, state);
    }

    @Override
    public List<Order> findOrdersByState(Boolean state) {
        return orderRepository.findAllByActive(state, Sort.by("orderDate"));
    }
}