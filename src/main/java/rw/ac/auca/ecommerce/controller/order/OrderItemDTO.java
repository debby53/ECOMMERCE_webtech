package rw.ac.auca.ecommerce.controller.order;

import java.util.UUID;

class OrderItemDTO {
    private UUID productId;
    private Integer quantity;

    public UUID getProductId() { return productId; }
    public void setProductId(UUID productId) { this.productId = productId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}