package ntq.uet.server.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ntq.uet.server.models.product.ProductModel;

public interface ProductRepository extends MongoRepository<ProductModel, String> {
    ProductModel findByProductCode(String code);
    List<ProductModel> findByProductName(String name);
}
