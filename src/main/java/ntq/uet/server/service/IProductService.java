package ntq.uet.server.service;

import ntq.uet.server.common.base.BaseResponse;
import ntq.uet.server.common.base.RequestContext;
import ntq.uet.server.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public interface IProductService {
    BaseResponse<?> createProduct(RequestContext ctx);
    BaseResponse<?> getAllProducts(RequestContext ctx);
    BaseResponse<?> getProducts(RequestContext ctx);
    BaseResponse<?> filterProducts(RequestContext ctx);
    BaseResponse<?> getProduct(RequestContext ctx, String productId);
    BaseResponse<?> updateProduct(RequestContext ctx, String productId);
    BaseResponse<?> deleteProduct(RequestContext ctx, String productId);
    BaseResponse<?> deleteAllProducts(RequestContext ctx);
}
