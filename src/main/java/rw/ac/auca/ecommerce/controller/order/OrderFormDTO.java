package rw.ac.auca.ecommerce.controller.order;

import java.util.List;
import java.util.UUID;

class OrderFormDTO {
    private UUID customerId;
    private String status;
    private List<OrderItemDTO> orderItems;

    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<OrderItemDTO> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItemDTO> orderItems) { this.orderItems = orderItems; }
}