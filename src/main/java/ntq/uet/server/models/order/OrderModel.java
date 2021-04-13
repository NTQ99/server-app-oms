package ntq.uet.server.models.order;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ntq.uet.server.models.customer.CustomerModel;

@Document(collection = "orders")
public class OrderModel {

    @Id
    private String id;

    private String orderCode;
    private String customerCode="";
    private String productCode="";
    private String deliveryUnit="";
    private CustomerModel.Address deliveryTo = new CustomerModel.Address();
    private Double shipFee=0.0;
    private String status="";
    private int quantity=0;
    private long createdAt;

    public OrderModel() {
        long now = System.currentTimeMillis();
        this.setOrderCode(String.valueOf(now));
        this.setCreatedAt(now);
    }
    public OrderModel(String customerCode, String productCode, String deliveryUnit, CustomerModel.Address deliveryTo, Double shipFee, String status,
            int quantity) {
        long now = System.currentTimeMillis();
        this.setOrderCode(String.valueOf(now));
        this.setCreatedAt(now);
        this.setCustomerCode(customerCode);
        this.setProductCode(productCode);
        this.setDeliveryUnit(deliveryUnit);
        this.setDeliveryTo(deliveryTo);
        this.setShipFee(shipFee);
        this.setStatus(status);
        this.setQuantity(quantity);
    }

    public String getId() {
        return id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderModel order = (OrderModel) o;

        return this.getCustomerCode().equals(order.getCustomerCode()) && this.getProductCode().equals(order.getProductCode())
            && this.getDeliveryUnit().equals(order.getDeliveryUnit()) && this.getDeliveryTo().equals(order.getDeliveryTo())
            && this.getQuantity() == order.getQuantity() && this.getShipFee().equals(order.getShipFee()) && this.getStatus().equals(order.getStatus());
    }

}
