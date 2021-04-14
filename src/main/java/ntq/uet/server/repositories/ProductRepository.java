package ntq.uet.server.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import ntq.uet.server.models.product.ProductModel;

public interface ProductRepository extends MongoRepository<ProductModel, String> {
    ProductModel findByProductCode(String code);
    ProductModel findOneByProductName(String name);
    Page<ProductModel> findByProductNameContainingIgnoreCase(String name, Pageable paging);
}
