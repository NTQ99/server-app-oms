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
import ntq.uet.server.model.common.Address;
import ntq.uet.server.model.entity.Customer;
import ntq.uet.server.model.payload.BasePageResponse;
import ntq.uet.server.model.payload.ErrorMessage;
import ntq.uet.server.service.CustomerService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;
	private final HttpServletRequest httpServletRequest;

    @PostMapping("/get")
    public ResponseEntity<?> getAll() {

        RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String userId = ctx.getAuthenticationId();

        List<Customer> customers = service.getAllCustomers(userId);

        return new ResponseEntity<>(new BasePageResponse<>(customers, ErrorMessage.StatusCode.OK.message), HttpStatus.OK);

    }

    @PostMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {

        RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String userId = ctx.getAuthenticationId();

        Customer customer = service.getCustomerById(id);

        if (!customer.validateUser(userId)) throw new GlobalException(ErrorMessage.StatusCode.UNAUTHORIZED.message);

        return new ResponseEntity<>(new BasePageResponse<>(customer, ErrorMessage.StatusCode.OK.message), HttpStatus.OK);

    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Customer customerData) {

        ServiceHeader serviceHeader = (ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER);
        RequestContext ctx = RequestContext.init(serviceHeader);

        String userId = ctx.getAuthenticationId();
        
        Customer currCustomerData = service.getCustomerByPhone(userId, customerData.getCustomerPhone());

        if (currCustomerData != null) {
            throw new GlobalException(ErrorMessage.StatusCode.EXIST.message);
        }

        customerData.setUserId(userId);
        BasePageResponse<Customer> response = new BasePageResponse<>(service.createCustomer(customerData), ErrorMessage.StatusCode.CREATED.message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id,
            @RequestBody Customer newCustomerData) {

        ServiceHeader serviceHeader = (ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER);
        RequestContext ctx = RequestContext.init(serviceHeader);

        String userId = ctx.getAuthenticationId();

        Customer currCustomerData = service.getCustomerById(id);

        if (currCustomerData == null) {
            throw new GlobalException(ErrorMessage.StatusCode.NOT_FOUND.message);
        }
        
        if (!currCustomerData.validateUser(userId)) {
            throw new GlobalException(ErrorMessage.StatusCode.UNAUTHORIZED.message);
        }

        BasePageResponse<Customer> response = new BasePageResponse<>(service.updateCustomer(currCustomerData, newCustomerData), ErrorMessage.StatusCode.MODIFIED.message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/create/address/{id}")
    public ResponseEntity<?> addAddress(@PathVariable("id") String id,
            @RequestBody Address newAddress) {

        ServiceHeader serviceHeader = (ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER);
        RequestContext ctx = RequestContext.init(serviceHeader);

        String userId = ctx.getAuthenticationId();

        Customer currCustomerData = service.getCustomerById(id);

        if (currCustomerData == null) {
            throw new GlobalException(ErrorMessage.StatusCode.NOT_FOUND.message);
        }
        
        if (!currCustomerData.validateUser(userId)) {
            throw new GlobalException(ErrorMessage.StatusCode.UNAUTHORIZED.message);
        }

        BasePageResponse<Customer> response = new BasePageResponse<>(service.addCustomerAddress(currCustomerData, newAddress), ErrorMessage.StatusCode.MODIFIED.message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/update/address/{id}/{index}")
    public ResponseEntity<?> updateAddress(@PathVariable("id") String id, @PathVariable("index") String index,
            @RequestBody Address newAddressData) {

        ServiceHeader serviceHeader = (ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER);
        RequestContext ctx = RequestContext.init(serviceHeader);

        String userId = ctx.getAuthenticationId();

        Customer currCustomerData = service.getCustomerById(id);

        if (currCustomerData == null) {
            throw new GlobalException(ErrorMessage.StatusCode.NOT_FOUND.message);
        }
        
        if (!currCustomerData.validateUser(userId)) {
            throw new GlobalException(ErrorMessage.StatusCode.UNAUTHORIZED.message);
        }
        
        BasePageResponse<Customer> response = new BasePageResponse<>(service.updateCustomerAddress(currCustomerData, Integer.parseInt(index), newAddressData), ErrorMessage.StatusCode.MODIFIED.message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/delete/address/{id}/{index}")
    public ResponseEntity<?> removeAddress(@PathVariable("id") String id, @PathVariable("index") String index) {

        ServiceHeader serviceHeader = (ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER);
        RequestContext ctx = RequestContext.init(serviceHeader);

        String userId = ctx.getAuthenticationId();

        Customer currCustomerData = service.getCustomerById(id);

        if (currCustomerData == null) {
            throw new GlobalException(ErrorMessage.StatusCode.NOT_FOUND.message);
        }
        
        if (!currCustomerData.validateUser(userId)) {
            throw new GlobalException(ErrorMessage.StatusCode.UNAUTHORIZED.message);
        }
        
        BasePageResponse<Customer> response = new BasePageResponse<>(service.removeCustomerAddress(currCustomerData, Integer.parseInt(index)), ErrorMessage.StatusCode.MODIFIED.message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {

        ServiceHeader serviceHeader = (ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER);
        RequestContext ctx = RequestContext.init(serviceHeader);

        String userId = ctx.getAuthenticationId();

        Customer currDeliveryData = service.getCustomerById(id);

        if (currDeliveryData == null) {
            throw new GlobalException(ErrorMessage.StatusCode.NOT_FOUND.message);
        }
        
        if (!currDeliveryData.validateUser(userId)) {
            throw new GlobalException(ErrorMessage.StatusCode.UNAUTHORIZED.message);
        }

        service.deleteCustomer(id);
        BasePageResponse<Customer> response = new BasePageResponse<>(null, ErrorMessage.StatusCode.OK.message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteAll() {

        ServiceHeader serviceHeader = (ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER);
        RequestContext ctx = RequestContext.init(serviceHeader);

        String userId = ctx.getAuthenticationId();

        service.deleteAllCustomers(userId);
        BasePageResponse<Customer> response = new BasePageResponse<>(null, ErrorMessage.StatusCode.OK.message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}