package ntq.uet.server.models.order;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ntq.uet.server.models.customer.CustomerModel;

@Document(collection = "orders")
public class OrderModel {

    enum Status {
        wait_for_confirmation, // chờ xác nhận từ người mua
        not_responded, // người mua không phản hồi
        cancel, // hủy đơn
        awaiting_transport, // chờ vận chuyển
        successful_delivery, // giao thành công
        failed_delivery // giao thất bại
    }

    @Id
    private String id;

    private String orderCode;
    private String customerCode = "";
    private String customerName = "";
    private String customerPhone = "";
    private String productCode = "";
    private String productName = "";
    private String deliveryUnit = "";
    private CustomerModel.Address deliveryTo = new CustomerModel.Address();
    private Double shipFee = 0.0;
    private Status status = Status.wait_for_confirmation;
    private int quantity = 0;
    private long createdAt;

    public OrderModel() {
        long now = System.currentTimeMillis();
        this.setOrderCode(String.valueOf(now));
        this.setCreatedAt(now);
    }

    public OrderModel(String customerCode, String customerName, String customerPhone, String productCode,
            String productName, String deliveryUnit, CustomerModel.Address deliveryTo, int quantity) {
        long now = System.currentTimeMillis();
        this.setOrderCode(String.valueOf(now));
        this.setCreatedAt(now);
        this.setCustomerCode(customerCode);
        this.setCustomerName(customerName);
        this.setCustomerPhone(customerPhone);
        this.setProductCode(productCode);
        this.setDeliveryUnit(deliveryUnit);
        this.setDeliveryTo(deliveryTo);
        this.setShipFee(shipFee);
        this.setQuantity(quantity);
    }

    public String getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public CustomerModel.Address getDeliveryTo() {
        return deliveryTo;
    }

    public void setDeliveryTo(CustomerModel.Address deliveryTo) {
        this.deliveryTo = deliveryTo;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getShipFee() {
        return shipFee;
    }

    public void setShipFee(Double shipFee) {
        this.shipFee = shipFee;
    }

    public String getDeliveryUnit() {
        return deliveryUnit;
    }

    public void setDeliveryUnit(String deliveryUnit) {
        this.deliveryUnit = deliveryUnit;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        OrderModel order = (OrderModel) o;

        return this.getCustomerCode().equals(order.getCustomerCode())
                && this.getProductCode().equals(order.getProductCode())
                && this.getDeliveryUnit().equals(order.getDeliveryUnit())
                && this.getDeliveryTo().equals(order.getDeliveryTo()) && this.getQuantity() == order.getQuantity()
                && this.getShipFee().equals(order.getShipFee()) && this.getStatus().equals(order.getStatus());
    }

}
