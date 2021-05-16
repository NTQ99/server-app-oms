package ntq.uet.server.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ntq.uet.server.models.DeliveryUnit;

public interface DeliveryUnitRepository extends MongoRepository<DeliveryUnit, String> {
    List<DeliveryUnit> findByUserId(String id);
    void deleteByUserId(String userId);
    DeliveryUnit findByDeliveryUnitName(String deliveryUnitName);
}