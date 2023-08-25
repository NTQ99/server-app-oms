package ntq.uet.server.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import ntq.uet.server.common.base.RequestContext;
import ntq.uet.server.common.base.ServiceHeader;
import ntq.uet.server.common.core.constant.CommonConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ntq.uet.server.common.exception.GlobalException;
import ntq.uet.server.model.entity.Product;
import ntq.uet.server.model.payload.BasePageResponse;
import ntq.uet.server.model.payload.ErrorMessage;
import ntq.uet.server.service.ProductService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;
    private final HttpServletRequest httpServletRequest;

    @PostMapping("/get")
    public ResponseEntity<?> getAll() {

        RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String userId = ctx.getAuthenticationId();

        List<Product> products = service.getAllProducts(userId);

        return new ResponseEntity<>(new BasePageResponse<>(products, ErrorMessage.StatusCode.OK.message), HttpStatus.OK);

    }

    @PostMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {

        RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String userId = ctx.getAuthenticationId();

        Product product = service.getProductById(id);

        if (!product.validateUser(userId)) throw new GlobalException(ErrorMessage.StatusCode.UNAUTHORIZED.message);

        return new ResponseEntity<>(new BasePageResponse<>(product, ErrorMessage.StatusCode.OK.message), HttpStatus.OK);

    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Product productData) {

        RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String userId = ctx.getAuthenticationId();
        
        Product currProductData = service.getProductByName(userId, productData.getProductName());

        if (currProductData != null) {
            throw new GlobalException(ErrorMessage.StatusCode.EXIST.message);
        }

        productData.setUserId(userId);
        BasePageResponse<Product> response = new BasePageResponse<>(service.createProduct(productData), ErrorMessage.StatusCode.CREATED.message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id,
            @RequestBody Product newProductData) {

        RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String userId = ctx.getAuthenticationId();

        Product currProductData = service.getProductById(id);

        if (currProductData == null) {
            throw new GlobalException(ErrorMessage.StatusCode.NOT_FOUND.message);
        }
        
        if (!currProductData.validateUser(userId)) {
            throw new GlobalException(ErrorMessage.StatusCode.UNAUTHORIZED.message);
        }

        BasePageResponse<Product> response = new BasePageResponse<>(service.updateProduct(currProductData, newProductData), ErrorMessage.StatusCode.MODIFIED.message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {

        RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String userId = ctx.getAuthenticationId();

        Product currDeliveryData = service.getProductById(id);

        if (currDeliveryData == null) {
            throw new GlobalException(ErrorMessage.StatusCode.NOT_FOUND.message);
        }
        
        if (!currDeliveryData.validateUser(userId)) {
            throw new GlobalException(ErrorMessage.StatusCode.UNAUTHORIZED.message);
        }

        service.deleteProduct(id);
        BasePageResponse<Product> response = new BasePageResponse<>(null, ErrorMessage.StatusCode.OK.message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteAll() {

        RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String userId = ctx.getAuthenticationId();

        service.deleteAllProducts(userId);
        BasePageResponse<Product> response = new BasePageResponse<>(null, ErrorMessage.StatusCode.OK.message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}