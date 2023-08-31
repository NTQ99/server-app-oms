package ntq.uet.server.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import ntq.uet.server.common.base.RequestContext;
import ntq.uet.server.common.base.ServiceHeader;
import ntq.uet.server.common.core.constant.CommonConstants;
import ntq.uet.server.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ntq.uet.server.common.exception.GlobalException;
import ntq.uet.server.model.entity.Product;
import ntq.uet.server.common.base.BaseResponse;
import ntq.uet.server.service.impl.ProductService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;
    private final HttpServletRequest httpServletRequest;

    @PostMapping("/get")
    public ResponseEntity<?> getAll() {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        String userId = ctx.getAuthenticationId();

        List<Product> products = service.getAllProducts(userId);

        return new ResponseEntity<>(new BaseResponse<>(products, ErrorCode.OK), HttpStatus.OK);

    }

    @PostMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        String userId = ctx.getAuthenticationId();

        Product product = service.getProductById(id);

        if (!product.validateUser(userId)) throw new GlobalException(ErrorCode.UNAUTHORIZED);

        return new ResponseEntity<>(new BaseResponse<>(product, ErrorCode.OK), HttpStatus.OK);

    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Product productData) {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        String userId = ctx.getAuthenticationId();
        
        Product currProductData = service.getProductByName(userId, productData.getProductName());

        if (currProductData != null) {
            throw new GlobalException(ErrorCode.EXIST);
        }

        productData.setUserId(userId);
        BaseResponse<Product> response = new BaseResponse<>(service.createProduct(productData), ErrorCode.CREATED);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id,
            @RequestBody Product newProductData) {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        String userId = ctx.getAuthenticationId();

        Product currProductData = service.getProductById(id);

        if (currProductData == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND);
        }
        
        if (!currProductData.validateUser(userId)) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED);
        }

        BaseResponse<Product> response = new BaseResponse<>(service.updateProduct(currProductData, newProductData), ErrorCode.MODIFIED);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        String userId = ctx.getAuthenticationId();

        Product currDeliveryData = service.getProductById(id);

        if (currDeliveryData == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND);
        }
        
        if (!currDeliveryData.validateUser(userId)) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED);
        }

        service.deleteProduct(id);
        BaseResponse<Product> response = new BaseResponse<>(null, ErrorCode.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteAll() {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        String userId = ctx.getAuthenticationId();

        service.deleteAllProducts(userId);
        BaseResponse<Product> response = new BaseResponse<>(null, ErrorCode.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}