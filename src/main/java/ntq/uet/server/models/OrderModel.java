package ntq.uet.server.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class OrderModel {

    @Id
    private String id;

    private String orderCode;
    private String customerCode;
    private String customerProduct;
    private String deliveryMethod;
    private CustomerModel.Address deliveryTo;
    private Double shipFee;
    private String status;
    private int quantity;
    private Date createdDate;

    public OrderModel() {
        long now = System.currentTimeMillis();
        this.setOrderCode(String.valueOf(now));
        this.setCreatedDate(new Date(now));
    }
    public OrderModel(String customerCode, String customerProduct, String deliveryMethod, CustomerModel.Address deliveryTo, Double shipFee, String status,
            int quantity) {
        long now = System.currentTimeMillis();
        this.setOrderCode(String.valueOf(now));
        this.setCreatedDate(new Date(now));
        this.setCustomerCode(customerCode);
        this.setCustomerProduct(customerProduct);
        this.setDeliveryMethod(deliveryMethod);
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getCustomerProduct() {
        return customerProduct;
    }

    public void setCustomerProduct(String customerProduct) {
        this.customerProduct = customerProduct;
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

}
