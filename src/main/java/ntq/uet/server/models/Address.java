package ntq.uet.server.models;

public class Address {
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