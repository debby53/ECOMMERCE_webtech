package rw.ac.auca.ecommerce.core.order.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.ac.auca.ecommerce.core.order.model.Order;
import rw.ac.auca.ecommerce.core.order.model.Order.OrderStatus;
import rw.ac.auca.ecommerce.core.customer.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The interface IOrderRepository.
 *
 * @author Jeremie Ukundwa Tuyisenge
 * @version 1.0
 */
@Repository
public interface IOrderRepository extends JpaRepository<Order, UUID> {

    @Query("SELECT o FROM Order o WHERE o.id = :id AND o.active = :active")
    Optional<Order> findOrderByIdWithNamedQuery(@Param("id") UUID id, @Param("active") Boolean active);

    Optional<Order> findByIdAndActive(UUID id, Boolean active);

    List<Order> findAllByCustomerAndActive(Customer customer, Boolean active);

    List<Order> findAllByStatusAndActive(OrderStatus status, Boolean active);

    List<Order> findAllByActive(Boolean active, Sort sort);
}