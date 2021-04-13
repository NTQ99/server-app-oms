package ntq.uet.server.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ntq.uet.server.models.customer.CustomerModel;

public interface CustomerRepository extends MongoRepository<CustomerModel, String> {
    CustomerModel findByCustomerCode(String customerCode);
    List<CustomerModel> findByCustomerName(String customerName);
    List<CustomerModel> findByCustomerPhone(String customerPhone);
}
