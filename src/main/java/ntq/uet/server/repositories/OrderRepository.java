package ntq.uet.server.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import ntq.uet.server.models.order.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
    Order findByOrderCode(String code);
    Page<Order> findByCustomerNameContainingIgnoreCase(String name, Pageable paging);
    Page<Order> findByCustomerPhoneContaining(String phone, Pageable paging);
    Page<Order> findByStatus(String status, Pageable paging);
    Page<Order> findByCustomerCode(String customerCode, Pageable paging);
    Page<Order> findByCustomerNameContainingIgnoreCaseAndStatus(String name, String status, Pageable paging);
    Page<Order> findByCustomerPhoneContainingAndStatus(String phone, String status, Pageable paging);
    Page<Order> findByCreatedAtBetween(String createdAtFrom, String createdAtTo, Pageable paging);
}
