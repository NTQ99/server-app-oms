package ntq.uet.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ntq.uet.server.models.Product;
import ntq.uet.server.repositories.ProductRepository;

@Service("productService")
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }
    
    public Product getProductByCode(String code) {
        return productRepository.findByProductCode(code);
    }

    public Product getProductByName(String name) {
        return productRepository.findOneByProductName(name);
    }

    public Page<Product> findProductByName(String name, Pageable paging) {
        return productRepository.findByProductNameContainingIgnoreCase(name, paging);
    }

    public Page<Product> getAllProducts(Pageable paging) {
        return productRepository.findAll(paging);
    }

    public Product updateProduct(String id, Product newProductData) {
        Product productData = productRepository.findById(id).orElse(null);
        if (productData == null) {
            return null;
        } else {
            productData.setProductName(newProductData.getProductName());
            productData.setProductDetail(newProductData.getProductDetail());
            productData.setProductPhotos(newProductData.getProductPhotos());
            productData.setPromotion(newProductData.getPromotion());
            productData.setRetailPrice(newProductData.getRetailPrice());
            productData.setWholesalePrice(newProductData.getWholesalePrice());
        }
        return productRepository.save(productData);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

}
