package ntq.uet.server.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import ntq.uet.server.common.base.BaseResponse;
import ntq.uet.server.common.base.RequestContext;
import ntq.uet.server.common.exception.ErrorCode;
import ntq.uet.server.common.exception.GlobalException;
import ntq.uet.server.service.ICustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ntq.uet.server.model.common.Address;
import ntq.uet.server.model.entity.Customer;
import ntq.uet.server.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public BaseResponse<?> createCustomer(RequestContext ctx) {

        Customer customerData = (Customer) ctx.getRequestData();
        String userId = ctx.getAuthenticationId();

        Customer currCustomerData = getCustomerByPhone(userId, customerData.getCustomerPhone());

        if (currCustomerData != null) {
            throw new GlobalException(ErrorCode.EXIST);
        }

        customerData.setUserId(userId);
        return new BaseResponse<>(createCustomer(customerData), ErrorCode.CREATED);
    }

    @Override
    public BaseResponse<?> getAllCustomers(RequestContext ctx) {

        String userId = ctx.getAuthenticationId();
        List<Customer> customers = customerRepository.findByUserId(userId);

        return new BaseResponse<>(customers, ErrorCode.OK);
    }

    @Override
    public BaseResponse<?> getCustomers(RequestContext ctx) {
        return null;
    }

    @Override
    public BaseResponse<?> filterCustomers(RequestContext ctx) {
        return null;
    }

    @Override
    public BaseResponse<?> getCustomer(RequestContext ctx, String customerId) {

        String userId = ctx.getAuthenticationId();

        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND);
        }

        if (!customer.validateUser(userId)) throw new GlobalException(ErrorCode.UNAUTHORIZED);

        return new BaseResponse<>(customer, ErrorCode.OK);
    }

    @Override
    public BaseResponse<?> updateCustomer(RequestContext ctx, String customerId) {

        Customer newCustomerData = (Customer) ctx.getRequestData();
        String userId = ctx.getAuthenticationId();

        Customer currCustomerData = customerRepository.findById(customerId).orElse(null);

        if (currCustomerData == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND);
        }

        if (!currCustomerData.validateUser(userId)) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED);
        }

        currCustomerData.setCustomerName(newCustomerData.getCustomerName());
        currCustomerData.setCustomerGender(newCustomerData.getCustomerGender());
        currCustomerData.setCustomerPhone(newCustomerData.getCustomerPhone());
        currCustomerData.setCustomerEmail(newCustomerData.getCustomerEmail());
        currCustomerData.setCustomerFacebook(newCustomerData.getCustomerFacebook());
        currCustomerData.setCustomerAddresses(newCustomerData.getCustomerAddresses());
        currCustomerData.setDefaultAddressId(newCustomerData.getDefaultAddressId());

        return new BaseResponse<>(customerRepository.save(currCustomerData), ErrorCode.MODIFIED);
    }

    @Override
    public BaseResponse<?> modifyCustomerAddress(RequestContext ctx, String customerId, int addressIndex) {
        Address newAddress = (Address) ctx.getRequestData();
        String userId = ctx.getAuthenticationId();

        Customer currCustomerData = customerRepository.findById(customerId).orElse(null);

        if (currCustomerData == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND);
        }

        if (!currCustomerData.validateUser(userId)) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED);
        }
        switch (addressIndex) {
            case -2:
                currCustomerData.addCustomerAddress(newAddress);
                break;
            case -1:
                currCustomerData.removeCustomerAddress(addressIndex);
                break;
            default:
                currCustomerData.updateCustomerAddress(addressIndex, newAddress);
                break;
        }

        return new BaseResponse<>(customerRepository.save(currCustomerData), ErrorCode.MODIFIED);
    }

    @Override
    public BaseResponse<?> deleteCustomer(RequestContext ctx, String customerId) {

        String userId = ctx.getAuthenticationId();

        Customer currDeliveryData = customerRepository.findById(customerId).orElse(null);

        if (currDeliveryData == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND);
        }

        if (!currDeliveryData.validateUser(userId)) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED);
        }

        customerRepository.deleteById(customerId);
        return new BaseResponse<>(null, ErrorCode.OK);
    }

    @Override
    public BaseResponse<?> deleteAllCustomers(RequestContext ctx) {
        String userId = ctx.getAuthenticationId();

        customerRepository.deleteByUserId(userId);
        return new BaseResponse<>(null, ErrorCode.OK);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomerByCode(String code) {
        return customerRepository.findByCustomerCode(code);
    }

    public Customer getCustomerByPhone(String userId, String phone) {
        List<Customer> customers = customerRepository.findByUserId(userId);
        customers = customers.stream().filter(customer -> customer.getCustomerPhone().equals(phone))
                .collect(Collectors.toList());
        if (customers.isEmpty()) {
            return null;
        }
        return customers.get(0);
    }

    public Page<Customer> findCustomerByPhone(String phone, Pageable paging) {
        return customerRepository.findByCustomerPhoneContaining(phone, paging);
    }

    public Customer addCustomerAddress(Customer customerData, Address newAddress) {

        customerData.addCustomerAddress(newAddress);

        return customerRepository.save(customerData);
    }
}
