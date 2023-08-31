package ntq.uet.server.service;

import ntq.uet.server.common.base.BaseResponse;
import ntq.uet.server.common.base.RequestContext;
import org.springframework.data.domain.Pageable;

public interface ICustomerService {
    BaseResponse<?> createCustomer(RequestContext ctx);
    BaseResponse<?> getAllCustomers(RequestContext ctx);
    BaseResponse<?> getCustomers(RequestContext ctx);
    BaseResponse<?> filterCustomers(RequestContext ctx);
    BaseResponse<?> getCustomer(RequestContext ctx, String customerId);
    BaseResponse<?> updateCustomer(RequestContext ctx, String customerId);
    BaseResponse<?> modifyCustomerAddress(RequestContext ctx, String customerId, int addressIndex);
    BaseResponse<?> deleteCustomer(RequestContext ctx, String customerId);
    BaseResponse<?> deleteAllCustomers(RequestContext ctx);

}
