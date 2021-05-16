package ntq.uet.server.services;

import java.util.List;
import java.util.stream.Collectors;

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

    public Customer getCustomerByPhone(String userId, String phone) {
        List<Customer> customers = customerRepository.findByUserId(userId);
        if (customers.isEmpty()) return null;
        return customers.stream().filter(customer -> customer.getCustomerPhone().equals(phone))
                .collect(Collectors.toList()).get(0);
    }

    public Page<Customer> findCustomerByPhone(String phone, Pageable paging) {
        return customerRepository.findByCustomerPhoneContaining(phone, paging);
    }

    public List<Customer> getAllCustomers(String userId) {
        return customerRepository.findByUserId(userId);
    }

    public Customer updateCustomer(Customer customerData, Customer newCustomerData) {

        customerData.setCustomerName(newCustomerData.getCustomerName());
        customerData.setCustomerPhone(newCustomerData.getCustomerPhone());

        return customerRepository.save(customerData);
    }

    public Customer addCustomerAddress(Customer customerData, Address newAddress) {

        customerData.addCustomerAddress(newAddress);

        return customerRepository.save(customerData);
    }

    public Customer updateCustomerAddress(Customer customerData, int index, Address newAddressData) {

        customerData.updateCustomerAddress(index, newAddressData);

        return customerRepository.save(customerData);
    }

    public Customer removeCustomerAddress(Customer customerData, int index) {

        customerData.removeCustomerAddress(index);

        return customerRepository.save(customerData);
    }

    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }

    public void deleteAllCustomers(String userId) {
        customerRepository.deleteByUserId(userId);
    }

}
