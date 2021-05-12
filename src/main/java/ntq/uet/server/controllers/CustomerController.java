package ntq.uet.server.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import ntq.uet.server.exceptions.ResourceNotFoundException;
import ntq.uet.server.models.Address;
import ntq.uet.server.models.customer.Customer;
import ntq.uet.server.payload.BasePageRequest;
import ntq.uet.server.payload.BasePageResponse;
import ntq.uet.server.services.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @PostMapping(consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasePageResponse<List<Customer>>> getAll(
            @RequestBody MultiValueMap<String, String> requestObject) {

        BasePageRequest request = BasePageRequest.getObject(requestObject);
        List<Customer> allCustomers = new ArrayList<>();
        Pageable paging = PageRequest.of(request.getPagination().getPage() - 1, request.getPagination().getPerpage());
        Page<Customer> pageCustomers;

        if (request.getQuery().getGeneralSearch() == "") {
            pageCustomers = service.getAllCustomers(paging);
        } else {
            pageCustomers = service.findCustomerByPhone(request.getQuery().getGeneralSearch(), paging);
        }

        allCustomers = pageCustomers.getContent();
        
        return new ResponseEntity<>(new BasePageResponse<>(allCustomers, pageCustomers.getNumber(),
                pageCustomers.getTotalPages(), pageCustomers.getSize(), pageCustomers.getTotalElements()),
                HttpStatus.OK);

    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> getById(@PathVariable("id") String id) {

        Customer customer = service.getCustomerById(id);

        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Not found Customer with id = " + id);
        }

    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customerData) {

        Customer tmp = service.getCustomerByPhone(customerData.getCustomerPhone());
        if (tmp == null) {
            return new ResponseEntity<>(service.createCustomer(customerData), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(tmp, HttpStatus.CONFLICT);
        }

    }

    @PutMapping("{id}")
    public ResponseEntity<Customer> update(@PathVariable("id") String id,
            @RequestBody Customer newCustomerData) {

        Customer currCustomerData = service.getCustomerById(id);
        if (currCustomerData == null) {
            throw new ResourceNotFoundException("Not found Customer with id = " + id);
        }

        if (currCustomerData.equals(newCustomerData)) {
            return new ResponseEntity<>(currCustomerData, HttpStatus.NOT_MODIFIED);
        }
        Customer newCustomerModified = service.updateCustomer(id, newCustomerData);
        return new ResponseEntity<>(newCustomerModified, HttpStatus.OK);

    }

    @PutMapping("{id}/address/add")
    public ResponseEntity<Customer> addAddress(@PathVariable("id") String id,
            @RequestBody Address newAddress) {

        Customer newCustomerModified = service.addCustomerAddress(id, newAddress);
        if (newCustomerModified != null) {
            return new ResponseEntity<>(newCustomerModified, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Not found Customer with id = " + id);
        }

    }

    @PutMapping("{id}/address/update")
    public ResponseEntity<Customer> updateAddress(@PathVariable("id") String id,
            @RequestBody Address[] address) {

        Customer newCustomerModified = service.updateCustomerAddress(id, address[0], address[1]);
        if (newCustomerModified != null) {
            return new ResponseEntity<>(newCustomerModified, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Not found Customer with id = " + id);
        }

    }

    @PutMapping("{id}/address/delete")
    public ResponseEntity<Customer> removeAddress(@PathVariable("id") String id,
            @RequestBody Address address) {

        Customer newCustomerModified = service.removeCustomerAddress(id, address);
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