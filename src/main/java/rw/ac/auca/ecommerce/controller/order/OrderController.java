package rw.ac.auca.ecommerce.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rw.ac.auca.ecommerce.core.customer.model.Customer;
import rw.ac.auca.ecommerce.core.customer.service.ICustomerService;
import rw.ac.auca.ecommerce.core.order.model.Order;
import rw.ac.auca.ecommerce.core.order.model.OrderItem;
import rw.ac.auca.ecommerce.core.order.service.IOrderService;
import rw.ac.auca.ecommerce.core.product.model.Product;
import rw.ac.auca.ecommerce.core.product.service.IProductService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * The class OrderController.
 *
 * @author Jeremie Ukundwa Tuyisenge
 * @version 1.0
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/order/")
public class OrderController {

    private final IOrderService orderService;
    private final ICustomerService customerService;
    private final IProductService productService;

    @GetMapping("/search/all")
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.findOrdersByState(Boolean.TRUE);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    @GetMapping("/create")
    public String getOrderCreationPage(Model model) {
        OrderFormDTO orderForm = new OrderFormDTO();
        orderForm.setOrderItems(new ArrayList<>(List.of(new OrderItemDTO())));
        model.addAttribute("orderForm", orderForm);
        model.addAttribute("customers", customerService.findCustomersByState(Boolean.TRUE));
        model.addAttribute("products", productService.findProductsByState(Boolean.TRUE));
        return "order/orderCreationPage";
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute("orderForm") OrderFormDTO orderForm, Model model) {
        if (Objects.nonNull(orderForm) && orderForm.getCustomerId() != null && orderForm.getOrderItems() != null) {
            Order order = new Order();
            order.setCustomer(customerService.findCustomerByIdAndState(orderForm.getCustomerId(), Boolean.TRUE));
            order.setStatus(Order.OrderStatus.valueOf(orderForm.getStatus()));
            order.setOrderDate(LocalDateTime.now());
            order.setActive(Boolean.TRUE);

            List<OrderItem> orderItems = orderForm.getOrderItems().stream()
                    .filter(item -> item.getProductId() != null && item.getQuantity() > 0)
                    .map(item -> {
                        OrderItem orderItem = new OrderItem();
                        Product product = productService.findProductByIdAndState(item.getProductId(), Boolean.TRUE);
                        orderItem.setProduct(product);
                        orderItem.setQuantity(item.getQuantity());
                        BigDecimal unitPrice = BigDecimal.valueOf(product.getPrice()); // Convert Double to BigDecimal
                        orderItem.setUnitPrice(unitPrice);
                        orderItem.setSubtotal(unitPrice.multiply(new BigDecimal(item.getQuantity())));
                        orderItem.setOrder(order);
                        return orderItem;
                    }).toList();

            order.setOrderItems(orderItems);
            order.setTotalAmount(orderItems.stream()
                    .map(OrderItem::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));

            orderService.createOrder(order);
            model.addAttribute("message", "Order Created Successfully");
            return "order/orderCreationPage";
        } else {
            model.addAttribute("error", "Order Creation Failed: Invalid form data");
            model.addAttribute("orderForm", orderForm != null ? orderForm : new OrderFormDTO());
            model.addAttribute("customers", customerService.findCustomersByState(Boolean.TRUE));
            model.addAttribute("products", productService.findProductsByState(Boolean.TRUE));
            return "order/orderCreationPage";
        }
    }
}