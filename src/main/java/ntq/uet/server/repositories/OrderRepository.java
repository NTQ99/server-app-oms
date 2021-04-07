package ntq.uet.server.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import ntq.uet.server.models.OrderModel;

public interface OrderRepository extends MongoRepository<OrderModel, String> {
    // OrderModel findByOderCode(String code);
}
