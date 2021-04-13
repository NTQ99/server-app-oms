package ntq.uet.server.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ntq.uet.server.models.BaseResponseModel;
import ntq.uet.server.models.customer.CustomerModel;
import ntq.uet.server.services.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping
    public BaseResponseModel<List<CustomerModel>> getAll(@RequestBody(required = false) String customerPhone) {
        BaseResponseModel<List<CustomerModel>> response;
        List<CustomerModel> allCustomers = new ArrayList<>();

        try {
            if (customerPhone == null) {
                allCustomers = service.getAllCustomers();
            } else {
                allCustomers = service.getCustomerByPhone(customerPhone);
            }
        } catch (Exception e) {
            return new BaseResponseModel<>("err", "internal_server_error");
        }

        if (!allCustomers.isEmpty()) {
            response = new BaseResponseModel<>("succ", "", allCustomers);
        } else {
            response = new BaseResponseModel<>("err", "not_found");
        }

        return response;
    }

    @GetMapping("{id}")
    public BaseResponseModel<CustomerModel> getById(@PathVariable("id") String id) {
        BaseResponseModel<CustomerModel> response;
        CustomerModel customer = service.getCustomerById(id);

        if (customer != null) {
            response = new BaseResponseModel<>("succ", "", customer);
        } else {
            return new BaseResponseModel<>("err", "internal_server_error");
        }

        return response;
    }

    @PostMapping
    public BaseResponseModel<CustomerModel> create(@RequestBody CustomerModel customer) {
        BaseResponseModel<CustomerModel> response;

        try {
            CustomerModel tmp = service.getCustomerByPhone(customer.getCustomerPhone()).get(0);
            if (tmp == null) {
                CustomerModel newCustomer = service.createCustomer(customer);
                response = new BaseResponseModel<>("succ", "", newCustomer);
            } else {
                response = new BaseResponseModel<>("succ", "exist", tmp);
            }
        } catch (Exception e) {
            return new BaseResponseModel<>("err", "internal_server_error");
        }

        return response;
    }

    @PutMapping("{id}")
    public BaseResponseModel<CustomerModel> update(@PathVariable("id") String id, @RequestBody CustomerModel newCustomerData) {
        BaseResponseModel<CustomerModel> response;

        try {
            CustomerModel currCustomerData = service.getCustomerById(id);
            if (currCustomerData.equals(newCustomerData)) {
                return new BaseResponseModel<>("err", "data_not_change", currCustomerData);
            }
            CustomerModel newCustomer = service.updateCustomer(id, newCustomerData);
            if (newCustomer != null) {
                response = new BaseResponseModel<>("succ", "", newCustomer);
            } else {
                response = new BaseResponseModel<>("err", "not_found");
            }
        } catch (Exception e) {
            return new BaseResponseModel<>("err", "internal_server_error");
        }

        return response;
    }

    @PutMapping("{id}/address/add")
    public BaseResponseModel<CustomerModel> addAddress(@PathVariable("id") String id, @RequestBody CustomerModel.Address newAddress) {
        BaseResponseModel<CustomerModel> response;

        try {
            CustomerModel newCustomer = service.addCustomerAddress(id, newAddress);
            if (newCustomer != null) {
                response = new BaseResponseModel<>("succ", "", newCustomer);
            } else {
                response = new BaseResponseModel<>("err", "not_found");
            }
        } catch (Exception e) {
            return new BaseResponseModel<>("err", "internal_server_error");
        }

        return response;
    }

    @PutMapping("{id}/address/update")
    public BaseResponseModel<CustomerModel> updateAddress(@PathVariable("id") String id, @RequestBody CustomerModel.Address [] address) {
        BaseResponseModel<CustomerModel> response;

        try {
            CustomerModel newCustomer = service.updateCustomerAddress(id, address[0], address[1]);
            if (newCustomer != null) {
                response = new BaseResponseModel<>("succ", "", newCustomer);
            } else {
                response = new BaseResponseModel<>("err", "not_found");
            }
        } catch (Exception e) {
            return new BaseResponseModel<>("err", "internal_server_error");
        }

        return response;
    }

    @PutMapping("{id}/address/delete")
    public BaseResponseModel<CustomerModel> removeAddress(@PathVariable("id") String id, @RequestBody CustomerModel.Address address) {
        BaseResponseModel<CustomerModel> response;

        try {
            CustomerModel newCustomer = service.removeCustomerAddress(id, address);
            if (newCustomer != null) {
                response = new BaseResponseModel<>("succ", "", newCustomer);
            } else {
                response = new BaseResponseModel<>("err", "not_found");
            }
        } catch (Exception e) {
            return new BaseResponseModel<>("err", "internal_server_error");
        }

        return response;
    }

    @DeleteMapping("{id}")
    public BaseResponseModel<?> delete(@PathVariable("id") String id) {
        BaseResponseModel<?> response;

        try {
            service.deleteCustomer(id);
            response = new BaseResponseModel<>("succ", "");
        } catch (Exception e) {
            return new BaseResponseModel<>("err", "internal_server_error");
        }

        return response;
    }

    @DeleteMapping
    public BaseResponseModel<?> deleteAll() {
        BaseResponseModel<?> response;

        try {
            service.deleteAllCustomers();
            response = new BaseResponseModel<>("succ", "");
        } catch (Exception e) {
            return new BaseResponseModel<>("err", "internal_server_error");
        }

        return response;
    }
}