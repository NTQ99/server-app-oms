package ntq.uet.server.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class Order {

    public enum Status {
        wait_confirm, // chờ xác nhận từ người mua
        not_responded, // người mua không phản hồi
        canceled, // hủy đơn
        await_trans, // chờ vận chuyển
        success, // giao thành công
        fail // giao thất bại
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
    private String note = "";
    private Address deliveryTo = new Address();
    private Double shipFee = 0.0;
    private Double totalPrice = 0.0;
    private Double codAmount = 0.0;
    private Status status = Status.wait_confirm;
    private int quantity = 0;
    private long createdAt;
    private long lastModifiedAt;

    public Order() {
        long now = System.currentTimeMillis();
        this.setOrderCode(String.format("%07d", now % 1046527));
        this.setCreatedAt(now);
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Order(String customerCode, String customerName, String customerPhone, String productCode,
            String productName, String deliveryUnit, Address deliveryTo, int quantity, String note) {
        long now = System.currentTimeMillis();
        this.setOrderCode(String.format("%07d", now % 1046527));
        this.setCreatedAt(now);
        this.setCustomerCode(customerCode);
        this.setCustomerName(customerName);
        this.setCustomerPhone(customerPhone);
        this.setProductCode(productCode);
        this.setDeliveryUnit(deliveryUnit);
        this.setDeliveryTo(deliveryTo);
        this.setShipFee(shipFee);
        this.setQuantity(quantity);
        this.setNote(note);
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

    public Address getDeliveryTo() {
        return deliveryTo;
    }

    public void setDeliveryTo(Address deliveryTo) {
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

        Order order = (Order) o;

        return this.getCustomerCode().equals(order.getCustomerCode())
                && this.getProductCode().equals(order.getProductCode())
                && this.getDeliveryUnit().equals(order.getDeliveryUnit())
                && this.getDeliveryTo().equals(order.getDeliveryTo()) && this.getQuantity() == order.getQuantity()
                && this.getShipFee().equals(order.getShipFee()) && this.getStatus().equals(order.getStatus())
                && this.getNote().equals(order.getNote());
    }

}
