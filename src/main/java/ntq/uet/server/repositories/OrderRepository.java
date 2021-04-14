package ntq.uet.server.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import ntq.uet.server.models.order.OrderModel;

public interface OrderRepository extends MongoRepository<OrderModel, String> {
    OrderModel findByOrderCode(String code);
    Page<OrderModel> findByCustomerNameContainingIgnoreCase(String name, Pageable paging);
    Page<OrderModel> findByCustomerPhoneContaining(String phone, Pageable paging);
    Page<OrderModel> findByStatus(String status, Pageable paging);
    Page<OrderModel> findByCustomerNameContainingIgnoreCaseAndStatus(String name, String status, Pageable paging);
    Page<OrderModel> findByCustomerPhoneContainingAndStatus(String phone, String status, Pageable paging);
    Page<OrderModel> findByCreatedAtBetween(String createdAtFrom, String createdAtTo, Pageable paging);
}
