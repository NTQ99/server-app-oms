package ntq.uet.server.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// customer model
@Document(collection = "customers")
public class CustomerModel {

    // address struct
    public static class Address {
        public String ward;
        public String district;
        public String province;
        // address detail
        public String detail;

        public Address() {}
        public Address(String ward, String district, String province, String detail) {
            this.ward = ward;
            this.district = district;
            this.province = province;
            this.detail = detail;
        };

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Address address = (Address) o;

            return this.ward.equals(address.ward) && this.district.equals(address.district)
                    && this.province.equals(address.province) && this.detail.equals(address.detail);
        }
    }

    @Id
    private String id;

    private String customerCode;
    private String customerName;
    private List<Address> customerAddresses = new ArrayList<>();
    private String customerPhone;
    private Date createdDate;

    public CustomerModel() {
        long now = System.currentTimeMillis();
        this.setCustomerCode(String.valueOf(now));
        this.setCreatedDate(new Date(now));
    };

    public CustomerModel(String customerName, Address customerAddress, String customerPhone) {
        long now = System.currentTimeMillis();
        this.setCustomerCode(String.valueOf(System.currentTimeMillis()));
        this.setCreatedDate(new Date(now));
        this.setCustomerName(customerName);
        this.addCustomerAddress(customerAddress);
        this.setCustomerPhone(customerPhone);
    };

    public String getId() {
        return id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public void addCustomerAddress(Address customerAddress) {
        if (!this.customerAddresses.contains(customerAddress)) {
            this.customerAddresses.add(customerAddress);
        }
    }

    public void updateCustomerAddress(Address currCustomerAddress, Address newCustomerAddress) {
        int index = this.customerAddresses.indexOf(currCustomerAddress);
        this.customerAddresses.set(index, newCustomerAddress);
    }
    
    public void removeCustomerAddress(Address customerAddress) {
        this.customerAddresses.remove(customerAddress);
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
}
