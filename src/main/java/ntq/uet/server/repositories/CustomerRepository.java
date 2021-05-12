package ntq.uet.server.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import ntq.uet.server.models.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    Customer findByCustomerCode(String customerCode);
    Page<Customer> findByCustomerNameContainingIgnoreCase(String customerName, Pageable paging);
    Page<Customer> findByCustomerPhoneContaining(String customerPhone, Pageable paging);
    Customer findOneByCustomerPhone(String customerPhone);
}
