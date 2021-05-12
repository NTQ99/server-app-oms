package ntq.uet.server.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import ntq.uet.server.models.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
    Product findByProductCode(String code);
    Product findOneByProductName(String name);
    Page<Product> findByProductNameContainingIgnoreCase(String name, Pageable paging);
}
