package ntq.uet.server.models.customer;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// customer model
@Document(collection = "customers")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerModel {

    // address struct
    public static class Address {
        private String ward="";
        private String district="";
        private String province="";
        // address detail
        private String detail="";

        public Address() {}
        
        public Address(String ward, String district, String province, String detail) {
            this.setDetail(detail);
            this.setDistrict(district);
            this.setProvince(province);
            this.setWard(ward);
        };

        public String getDetail() {
            return detail;
        }
        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getWard() {
            return ward;
        }

        public void setWard(String ward) {
            this.ward = ward;
        }

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

    private String customerCode="";
    private String customerName="";
    private List<Address> customerAddresses = new ArrayList<>();
    private String customerPhone="";
    private long createdAt;

    public CustomerModel() {
        long now = System.currentTimeMillis();
        this.setCustomerCode(String.valueOf(now));
        this.setCreatedAt(now);
    };

    public CustomerModel(String customerName, String customerPhone) {
        long now = System.currentTimeMillis();
        this.setCustomerCode(String.valueOf(System.currentTimeMillis()));
        this.setCreatedAt(now);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerModel customer = (CustomerModel) o;

        return this.getCustomerName().equals(customer.getCustomerName()) && this.getCustomerPhone().equals(customer.getCustomerPhone());
    }
}
