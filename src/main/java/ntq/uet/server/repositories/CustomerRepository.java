package ntq.uet.server.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import ntq.uet.server.models.customer.CustomerModel;

public interface CustomerRepository extends MongoRepository<CustomerModel, String> {
    CustomerModel findByCustomerCode(String customerCode);
    Page<CustomerModel> findByCustomerNameContainingIgnoreCase(String customerName, Pageable paging);
    Page<CustomerModel> findByCustomerPhoneContaining(String customerPhone, Pageable paging);
    CustomerModel findOneByCustomerPhone(String customerPhone);
}
