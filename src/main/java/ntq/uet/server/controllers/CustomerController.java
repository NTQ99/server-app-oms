package ntq.uet.server.controllers;

import java.util.List;

import ntq.uet.server.common.base.RequestContext;
import ntq.uet.server.common.base.ServiceHeader;
import ntq.uet.server.common.core.constant.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ntq.uet.server.exceptions.GlobalException;
import ntq.uet.server.models.Address;
import ntq.uet.server.models.Customer;
import ntq.uet.server.payload.BasePageResponse;
import ntq.uet.server.payload.ErrorMessage;
import ntq.uet.server.security.jwt.JwtUtils;
import ntq.uet.server.services.CustomerService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/customer")
public class

CustomerController {

    @Autowired
    private CustomerService service;
    @Autowired
	private JwtUtils jwtUtils;

    @PostMapping("/get")
    public ResponseEntity<?> getAll(HttpServletRequest httpServletRequest) {
        ServiceHeader serviceHeader = (ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER);
        RequestContext ctx = RequestContext.init(serviceHeader);

        String userId = ctx.getAuthenticationId();

        List<Customer> customers = service.getAllCustomers(userId);

        return new ResponseEntity<>(new BasePageResponse<>(customers, ErrorMessage.StatusCode.OK.message), HttpStatus.OK);

    }

    @PostMapping("/get/{id}")
    public ResponseEntity<?> getById(@RequestHeader("Authorization") String jwt, @PathVariable("id") String id) {

        String userId = jwtUtils.getIdFromJwtToken(jwt.substring(7, jwt.length()));

        Customer customer = service.getCustomerById(id);

        if (!customer.validateUser(userId)) throw new GlobalException(ErrorMessage.StatusCode.UNAUTHORIZED.message);

        return new ResponseEntity<>(new BasePageResponse<>(customer, ErrorMessage.StatusCode.OK.message), HttpStatus.OK);

    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestHeader("Authorization") String jwt, @RequestBody Customer customerData) {

        String userId = jwtUtils.getIdFromJwtToken(jwt.substring(7, jwt.length()));
        
        Customer currCustomerData = service.getCustomerByPhone(userId, customerData.getCustomerPhone());

        if (currCustomerData != null) {
            throw new GlobalException(ErrorMessage.StatusCode.EXIST.message);
        }

        customerData.setUserId(userId);
        BasePageResponse<Customer> response = new BasePageResponse<>(service.createCustomer(customerData), ErrorMessage.StatusCode.CREATED.message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestHeader("Authorization") String jwt, @PathVariable("id") String id,
            @RequestBody Customer newCustomerData) {

        String userId = jwtUtils.getIdFromJwtToken(jwt.substring(7, jwt.length()));

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
    public ResponseEntity<?> addAddress(@RequestHeader("Authorization") String jwt, @PathVariable("id") String id,
            @RequestBody Address newAddress) {

        String userId = jwtUtils.getIdFromJwtToken(jwt.substring(7, jwt.length()));

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
    public ResponseEntity<?> updateAddress(@RequestHeader("Authorization") String jwt, @PathVariable("id") String id, @PathVariable("index") String index,
            @RequestBody Address newAddressData) {

        String userId = jwtUtils.getIdFromJwtToken(jwt.substring(7, jwt.length()));

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
    public ResponseEntity<?> removeAddress(@RequestHeader("Authorization") String jwt, @PathVariable("id") String id, @PathVariable("index") String index) {

        String userId = jwtUtils.getIdFromJwtToken(jwt.substring(7, jwt.length()));

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
    public ResponseEntity<?> delete(@RequestHeader("Authorization") String jwt, @PathVariable("id") String id) {

        String userId = jwtUtils.getIdFromJwtToken(jwt.substring(7, jwt.length()));

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
    public ResponseEntity<?> deleteAll(@RequestHeader("Authorization") String jwt) {

        String userId = jwtUtils.getIdFromJwtToken(jwt.substring(7, jwt.length()));

        service.deleteAllCustomers(userId);
        BasePageResponse<Customer> response = new BasePageResponse<>(null, ErrorMessage.StatusCode.OK.message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}