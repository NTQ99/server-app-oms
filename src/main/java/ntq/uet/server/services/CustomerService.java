package ntq.uet.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ntq.uet.server.models.Address;
import ntq.uet.server.models.Customer;
import ntq.uet.server.repositories.CustomerRepository;

@Service("customerService")
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(String id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer getCustomerByCode(String code) {
        return customerRepository.findByCustomerCode(code);
    }

    public Customer getCustomerByPhone(String phone){
        return customerRepository.findOneByCustomerPhone(phone);
    }

    public Page<Customer> findCustomerByPhone(String phone, Pageable paging) {
        return customerRepository.findByCustomerPhoneContaining(phone, paging);
    }

    public Page<Customer> getAllCustomers(Pageable paging) {
        return customerRepository.findAll(paging);
    }

    public Customer updateCustomer(String id, Customer newCustomerData) {
        Customer customerData = customerRepository.findById(id).orElse(null);
        if (customerData == null) {
            return null;
        } else {
            customerData.setCustomerName(newCustomerData.getCustomerName());
            customerData.setCustomerPhone(newCustomerData.getCustomerPhone());
        }
        return customerRepository.save(customerData);
    }

    public Customer addCustomerAddress(String id, Address newAddress) {
        Customer customerData = customerRepository.findById(id).orElse(null);
        if (customerData == null) {
            return null;
        } else {
            customerData.addCustomerAddress(newAddress);
        }
        return customerRepository.save(customerData);
    }

    public Customer updateCustomerAddress(String id, Address currAddress, Address newAddress) {
        Customer customerData = customerRepository.findById(id).orElse(null);
        if (customerData == null) {
            return null;
        } else {
            customerData.updateCustomerAddress(currAddress, newAddress);
        }
        return customerRepository.save(customerData);
    }

    public Customer removeCustomerAddress(String id, Address address) {
        Customer customerData = customerRepository.findById(id).orElse(null);
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
