package ntq.uet.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntq.uet.server.models.product.ProductModel;
import ntq.uet.server.repositories.ProductRepository;

@Service("productService")
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductModel createProduct(ProductModel product) {
        return productRepository.save(product);
    }

    public ProductModel getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }
    
    public ProductModel getProductByCode(String code) {
        return productRepository.findByProductCode(code);
    }

    public List<ProductModel> getProductByName(String name) {
        return productRepository.findByProductName(name);
    }

    public List<ProductModel> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductModel updateProduct(String id, ProductModel newProductData) {
        ProductModel productData = productRepository.findById(id).orElse(null);
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
