package ntq.uet.server.controller;

import lombok.RequiredArgsConstructor;
import ntq.uet.server.common.base.RequestContext;
import ntq.uet.server.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ntq.uet.server.common.exception.GlobalException;
import ntq.uet.server.model.common.Address;
import ntq.uet.server.model.entity.Customer;
import ntq.uet.server.common.base.BaseResponse;
import ntq.uet.server.service.impl.CustomerService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;
	private final HttpServletRequest httpServletRequest;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Customer customerData) {

        RequestContext ctx = RequestContext.init(httpServletRequest);
        ctx.setRequestData(customerData);

        return new ResponseEntity<>(service.createCustomer(ctx), HttpStatus.OK);
    }

    @PostMapping("/get")
    public ResponseEntity<?> getAll() {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        return new ResponseEntity<>(service.getAllCustomers(ctx), HttpStatus.OK);

    }

    @PostMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        return new ResponseEntity<>(service.getCustomer(ctx, id), HttpStatus.OK);

    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id,
            @RequestBody Customer newCustomerData) {

        RequestContext ctx = RequestContext.init(httpServletRequest);
        ctx.setRequestData(newCustomerData);

        return new ResponseEntity<>(service.updateCustomer(ctx, id), HttpStatus.OK);
    }

    @PostMapping("/create/address/{id}")
    public ResponseEntity<?> addAddress(@PathVariable("id") String id,
            @RequestBody Address newAddress) {

        RequestContext ctx = RequestContext.init(httpServletRequest);
        ctx.setRequestData(newAddress);

        return new ResponseEntity<>(service.modifyCustomerAddress(ctx, id, -2), HttpStatus.OK);
    }

    @PostMapping("/update/address/{id}/{index}")
    public ResponseEntity<?> updateAddress(@PathVariable("id") String id, @PathVariable("index") int index,
            @RequestBody Address newAddress) {

        RequestContext ctx = RequestContext.init(httpServletRequest);
        ctx.setRequestData(newAddress);

        return new ResponseEntity<>(service.modifyCustomerAddress(ctx, id, index), HttpStatus.OK);
    }

    @PostMapping("/delete/address/{id}/{index}")
    public ResponseEntity<?> removeAddress(@PathVariable("id") String id, @PathVariable("index") int index) {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        return new ResponseEntity<>(service.modifyCustomerAddress(ctx, id, index), HttpStatus.OK);

    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        return new ResponseEntity<>(service.deleteCustomer(ctx, id), HttpStatus.OK);

    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteAll() {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        return new ResponseEntity<>(service.deleteAllCustomers(ctx), HttpStatus.OK);

    }
}