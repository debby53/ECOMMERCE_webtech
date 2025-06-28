//package rw.ac.auca.ecommerce.core.order.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import rw.ac.auca.ecommerce.core.base.AbstractBaseEntity;
//import rw.ac.auca.ecommerce.core.customer.model.Customer;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * The class Order.
// *
// * @author Jeremie Ukundwa Tuyisenge
// * @version 1.0
// */
//@Getter
//@Setter
//@Entity
//@NoArgsConstructor
//@Table(name = "orders")
//public class OrderItem extends AbstractBaseEntity {
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "customer_id", nullable = false)
//    private Customer customer;
//
//    @Column(name = "order_date", nullable = false)
//    private LocalDateTime orderDate;
//
//    @Column(name = "total_amount", nullable = false)
//    private BigDecimal totalAmount;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "status", nullable = false)
//    private OrderStatus status;
//
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<OrderItem> orderItems = new ArrayList<>();
//
//    public enum OrderStatus {
//        PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED
//    }
//}
package rw.ac.auca.ecommerce.core.order.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.ac.auca.ecommerce.core.base.AbstractBaseEntity;
import rw.ac.auca.ecommerce.core.product.model.Product;

import java.math.BigDecimal;

/**
 * The class OrderItem.
 *
 * @author Jeremie Ukundwa Tuyisenge
 * @version 1.0
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItem extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;
}