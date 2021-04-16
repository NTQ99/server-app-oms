package ntq.uet.server.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ntq.uet.server.exceptions.ResourceNotFoundException;
import ntq.uet.server.models.PageResponseModel;
import ntq.uet.server.models.customer.CustomerModel;
import ntq.uet.server.services.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping
    public ResponseEntity<PageResponseModel<CustomerModel>> getAll(@RequestParam(required = false) String customerPhone,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        List<CustomerModel> allCustomers = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<CustomerModel> pageCustomers;

        if (customerPhone == null) {
            pageCustomers = service.getAllCustomers(paging);
        } else {
            pageCustomers = service.findCustomerByPhone(customerPhone, paging);
        }

        allCustomers = pageCustomers.getContent();

        if (allCustomers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(
                    new PageResponseModel<>(allCustomers, pageCustomers.getNumber(), pageCustomers.getTotalPages(),
                            pageCustomers.getSize(), pageCustomers.getTotalElements()),
                    HttpStatus.OK);
        }

    }

    @GetMapping("{id}")
    public ResponseEntity<CustomerModel> getById(@PathVariable("id") String id) {

        CustomerModel customer = service.getCustomerById(id);

        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Not found Customer with id = " + id);
        }

    }

    @PostMapping
    public ResponseEntity<CustomerModel> create(@RequestBody CustomerModel customerData) {

        CustomerModel tmp = service.getCustomerByPhone(customerData.getCustomerPhone());
        if (tmp == null) {
            return new ResponseEntity<>(service.createCustomer(customerData), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(tmp, HttpStatus.CONFLICT);
        }

    }

    @PutMapping("{id}")
    public ResponseEntity<CustomerModel> update(@PathVariable("id") String id,
            @RequestBody CustomerModel newCustomerData) {

        CustomerModel currCustomerData = service.getCustomerById(id);
        if (currCustomerData == null) {
            throw new ResourceNotFoundException("Not found Customer with id = " + id);
        }

        if (currCustomerData.equals(newCustomerData)) {
            return new ResponseEntity<>(currCustomerData, HttpStatus.NOT_MODIFIED);
        }
        CustomerModel newCustomerModified = service.updateCustomer(id, newCustomerData);
        return new ResponseEntity<>(newCustomerModified, HttpStatus.OK);

    }

    @PutMapping("{id}/address/add")
    public ResponseEntity<CustomerModel> addAddress(@PathVariable("id") String id,
            @RequestBody CustomerModel.Address newAddress) {

        CustomerModel newCustomerModified = service.addCustomerAddress(id, newAddress);
        if (newCustomerModified != null) {
            return new ResponseEntity<>(newCustomerModified, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Not found Customer with id = " + id);
        }

    }

    @PutMapping("{id}/address/update")
    public ResponseEntity<CustomerModel> updateAddress(@PathVariable("id") String id,
            @RequestBody CustomerModel.Address[] address) {

        CustomerModel newCustomerModified = service.updateCustomerAddress(id, address[0], address[1]);
        if (newCustomerModified != null) {
            return new ResponseEntity<>(newCustomerModified, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Not found Customer with id = " + id);
        }

    }

    @PutMapping("{id}/address/delete")
    public ResponseEntity<CustomerModel> removeAddress(@PathVariable("id") String id,
            @RequestBody CustomerModel.Address address) {

        CustomerModel newCustomerModified = service.removeCustomerAddress(id, address);
        if (newCustomerModified != null) {
            return new ResponseEntity<>(newCustomerModified, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Not found Customer with id = " + id);
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {

        service.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {

        service.deleteAllCustomers();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}