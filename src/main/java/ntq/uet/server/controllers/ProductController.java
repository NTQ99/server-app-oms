package ntq.uet.server.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ntq.uet.server.exceptions.ResourceNotFoundException;
import ntq.uet.server.models.PageResponseModel;
import ntq.uet.server.models.product.ProductModel;
import ntq.uet.server.services.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<PageResponseModel<ProductModel>> getAll(@RequestParam(required = false) String productName,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        List<ProductModel> allProducts = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<ProductModel> pageProducts;

        if (productName == null) {
            pageProducts = service.getAllProducts(paging);
        } else {
            pageProducts = service.findProductByName(productName, paging);
        }

        allProducts = pageProducts.getContent();

        if (!allProducts.isEmpty()) {
            return new ResponseEntity<>(new PageResponseModel<>(allProducts, pageProducts.getNumber(),
                    pageProducts.getTotalPages(), pageProducts.getSize(), pageProducts.getTotalElements()),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    @GetMapping("{id}")
    public ResponseEntity<ProductModel> getById(@PathVariable("id") String id) {

        ProductModel product = service.getProductById(id);

        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Not found Product with id = " + id);
        }

    }

    @PostMapping
    public ResponseEntity<ProductModel> create(@RequestBody ProductModel product) {

        ProductModel tmp = service.getProductByName(product.getProductName());
        if (tmp == null) {
            return new ResponseEntity<>(service.createProduct(product), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(tmp, HttpStatus.CONFLICT);
        }

    }

    @PutMapping("{id}")
    public ResponseEntity<ProductModel> update(@PathVariable("id") String id,
            @RequestBody ProductModel newProductData) {

        ProductModel currProductData = service.getProductById(id);
        if (currProductData == null) {
            throw new ResourceNotFoundException("Not found Product with id =" + id);
        }

        if (currProductData.equals(newProductData)) {
            return new ResponseEntity<>(currProductData, HttpStatus.NOT_MODIFIED);
        }
        ProductModel newProductModified = service.updateProduct(id, newProductData);
        return new ResponseEntity<>(newProductModified, HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {

        service.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {

        service.deleteAllProducts();
        return new ResponseEntity<>(HttpStatus.OK);

    }
}