package ntq.uet.server.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ntq.uet.server.exceptions.ResourceNotFoundException;
import ntq.uet.server.models.product.Product;
import ntq.uet.server.payload.BasePageResponse;
import ntq.uet.server.services.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BasePageResponse<List<Product>>> getAll(@RequestParam(required = false) String productName,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        List<Product> allProducts = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<Product> pageProducts;

        if (productName == null) {
            pageProducts = service.getAllProducts(paging);
        } else {
            pageProducts = service.findProductByName(productName, paging);
        }

        allProducts = pageProducts.getContent();

        if (!allProducts.isEmpty()) {
            return new ResponseEntity<>(new BasePageResponse<>(allProducts, pageProducts.getNumber(),
                    pageProducts.getTotalPages(), pageProducts.getSize(), pageProducts.getTotalElements()),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getById(@PathVariable("id") String id) {

        Product product = service.getProductById(id);

        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Not found Product with id = " + id);
        }

    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {

        Product tmp = service.getProductByName(product.getProductName());
        if (tmp == null) {
            return new ResponseEntity<>(service.createProduct(product), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(tmp, HttpStatus.CONFLICT);
        }

    }

    @PutMapping("{id}")
    public ResponseEntity<Product> update(@PathVariable("id") String id,
            @RequestBody Product newProductData) {

        Product currProductData = service.getProductById(id);
        if (currProductData == null) {
            throw new ResourceNotFoundException("Not found Product with id =" + id);
        }

        if (currProductData.equals(newProductData)) {
            return new ResponseEntity<>(currProductData, HttpStatus.NOT_MODIFIED);
        }
        Product newProductModified = service.updateProduct(id, newProductData);
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