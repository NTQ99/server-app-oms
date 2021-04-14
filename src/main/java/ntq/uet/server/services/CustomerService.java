package ntq.uet.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ntq.uet.server.models.customer.CustomerModel;
import ntq.uet.server.repositories.CustomerRepository;

@Service("customerService")
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public CustomerModel createCustomer(CustomerModel customer) {
        return customerRepository.save(customer);
    }

    public CustomerModel getCustomerById(String id) {
        return customerRepository.findById(id).orElse(null);
    }

    public CustomerModel getCustomerByCode(String code) {
        return customerRepository.findByCustomerCode(code);
    }

    public CustomerModel getCustomerByPhone(String phone){
        return customerRepository.findOneByCustomerPhone(phone);
    }

    public Page<CustomerModel> findCustomerByPhone(String phone, Pageable paging) {
        return customerRepository.findByCustomerPhoneContaining(phone, paging);
    }

    public Page<CustomerModel> getAllCustomers(Pageable paging) {
        return customerRepository.findAll(paging);
    }

    public CustomerModel updateCustomer(String id, CustomerModel newCustomerData) {
        CustomerModel customerData = customerRepository.findById(id).orElse(null);
        if (customerData == null) {
            return null;
        } else {
            customerData.setCustomerName(newCustomerData.getCustomerName());
            customerData.setCustomerPhone(newCustomerData.getCustomerPhone());
        }
        return customerRepository.save(customerData);
    }

    public CustomerModel addCustomerAddress(String id, CustomerModel.Address newAddress) {
        CustomerModel customerData = customerRepository.findById(id).orElse(null);
        if (customerData == null) {
            return null;
        } else {
            customerData.addCustomerAddress(newAddress);
        }
        return customerRepository.save(customerData);
    }

    public CustomerModel updateCustomerAddress(String id, CustomerModel.Address currAddress, CustomerModel.Address newAddress) {
        CustomerModel customerData = customerRepository.findById(id).orElse(null);
        if (customerData == null) {
            return null;
        } else {
            customerData.updateCustomerAddress(currAddress, newAddress);
        }
        return customerRepository.save(customerData);
    }

    public CustomerModel removeCustomerAddress(String id, CustomerModel.Address address) {
        CustomerModel customerData = customerRepository.findById(id).orElse(null);
        if (customerData == null) {
            return null;
        } else {
            customerData.removeCustomerAddress(address);
        }
        return customerRepository.save(customerData);
    }

    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }

    public void deleteAllCustomers() {
        customerRepository.deleteAll();
    }

}
