package ntq.uet.server.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ntq.uet.server.models.BaseResponseModel;
import ntq.uet.server.models.product.ProductModel;
import ntq.uet.server.services.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public BaseResponseModel<List<ProductModel>> getAll(@RequestBody(required = false) String productName) {
        BaseResponseModel<List<ProductModel>> response;
        List<ProductModel> allProducts = new ArrayList<>();

        try {
            if (productName == null) {
                allProducts = service.getAllProducts();
            } else {
                allProducts = service.getProductByName(productName);
            }
        } catch (Exception e) {
            return new BaseResponseModel<>("err", "internal_server_error");
        }

        if (!allProducts.isEmpty()) {
            response = new BaseResponseModel<>("succ", "", allProducts);
        } else {
            response = new BaseResponseModel<>("err", "not_found");
        }

        return response;
    }

    @GetMapping("{id}")
    public BaseResponseModel<ProductModel> getById(@PathVariable("id") String id) {
        BaseResponseModel<ProductModel> response;
        ProductModel product = service.getProductById(id);

        if (product != null) {
            response = new BaseResponseModel<>("succ", "", product);
        } else {
            return new BaseResponseModel<>("err", "internal_server_error");
        }

        return response;
    }

    @PostMapping
    public BaseResponseModel<ProductModel> create(@RequestBody ProductModel product) {
        BaseResponseModel<ProductModel> response;

        try {
            ProductModel tmp = service.getProductByName(product.getProductName()).get(0);
            if (tmp == null) {
                ProductModel newProduct = service.createProduct(product);
                response = new BaseResponseModel<>("succ", "", newProduct);
            } else {
                response = new BaseResponseModel<>("succ", "exist", tmp);
            }
        } catch (Exception e) {
            return new BaseResponseModel<>("err", "internal_server_error");
        }

        return response;
    }

    @PutMapping("{id}")
    public BaseResponseModel<ProductModel> update(@PathVariable("id") String id, @RequestBody ProductModel newProductData) {
        BaseResponseModel<ProductModel> response;

        try {
            ProductModel currProductData = service.getProductById(id);
            if (currProductData.equals(newProductData)) {
                return new BaseResponseModel<>("err", "data_not_change", currProductData);
            }
            ProductModel newProduct = service.updateProduct(id, newProductData);
            if (newProduct != null) {
                response = new BaseResponseModel<>("succ", "", newProduct);
            } else {
                response = new BaseResponseModel<>("err", "not_found");
            }
        } catch (Exception e) {
            return new BaseResponseModel<>("err", "internal_server_error");
        }

        return response;
    }

    @DeleteMapping("{id}")
    public BaseResponseModel<?> delete(@PathVariable("id") String id) {
        BaseResponseModel<?> response;

        try {
            service.deleteProduct(id);
            response = new BaseResponseModel<>("succ", "");
        } catch (Exception e) {
            return new BaseResponseModel<>("err", "internal_server_error");
        }

        return response;
    }

    @DeleteMapping
    public BaseResponseModel<?> deleteAll() {
        BaseResponseModel<?> response;

        try {
            service.deleteAllProducts();
            response = new BaseResponseModel<>("succ", "");
        } catch (Exception e) {
            return new BaseResponseModel<>("err", "internal_server_error");
        }

        return response;
    }
}