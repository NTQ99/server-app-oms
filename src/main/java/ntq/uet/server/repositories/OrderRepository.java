package ntq.uet.server.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import ntq.uet.server.models.order.OrderModel;

public interface OrderRepository extends MongoRepository<OrderModel, String> {
    OrderModel findByOrderCode(String code);
}
