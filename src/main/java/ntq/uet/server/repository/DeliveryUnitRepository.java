package ntq.uet.server.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ntq.uet.server.model.entity.DeliveryUnit;

public interface DeliveryUnitRepository extends MongoRepository<DeliveryUnit, String> {
    List<DeliveryUnit> findByUserId(String id);
    void deleteByUserId(String userId);
    DeliveryUnit findByDeliveryUnitName(String deliveryUnitName);
}