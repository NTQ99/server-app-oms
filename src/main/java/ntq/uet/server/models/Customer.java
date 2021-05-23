package ntq.uet.server.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ntq.uet.server.exceptions.GlobalException;

// customer model
@Document(collection = "customers")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer {

    @Id
    private String id;

    private String userId;
    private String customerCode;
    private String customerName;
    private List<Address> customerAddresses = new ArrayList<>();
    private String customerPhone;
    private long createdAt;

    public Customer() {
        long now = System.currentTimeMillis();
        this.setCustomerCode(String.format("%07d", now % 1046527));
        this.setCreatedAt(now);
    };

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Customer(String userId, String customerName, String customerPhone) {
        long now = System.currentTimeMillis();
        this.setCustomerCode(String.format("%07d", now % 1046527));
        this.setCreatedAt(now);
        this.setUserId(userId);
        this.setCustomerName(customerName);
        this.setCustomerPhone(customerPhone);
    };

    public String getId() {
        return id;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public List<Address> getCustomerAddresses() {
        return customerAddresses;
    }

    public void setCustomerAddresses(List<Address> customerAddresses) {
        this.customerAddresses = customerAddresses;
    }

    public void addCustomerAddress(Address customerAddress) {
        if (!this.customerAddresses.contains(customerAddress)) {
            this.customerAddresses.add(customerAddress);
        }
    }

    public void updateCustomerAddress(int index, Address newCustomerAddress) {
        if (!this.customerAddresses.contains(newCustomerAddress)) {
            this.customerAddresses.set(index, newCustomerAddress);
        }
    }
    
    public void removeCustomerAddress(int index) {
        this.customerAddresses.remove(index);
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return this.getCustomerName().equals(customer.getCustomerName()) && this.getCustomerPhone().equals(customer.getCustomerPhone());
    }

    public boolean validateUser(String userId) {
        return this.getUserId().equals(userId);
    }

    public void validateRequest() {
        if (this.getCustomerPhone() == null) throw new GlobalException("customer phone not null");
        if (this.getCustomerName() == null) throw new GlobalException("customer name not null");
    }
}