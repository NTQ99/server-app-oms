package ntq.uet.server.models.order;

import ntq.uet.server.models.customer.CustomerModel;
import ntq.uet.server.models.product.ProductModel;

public class OrderView {

    private String id;

    private String orderCode;
    private String customerName;
    private String customerPhone;
    private CustomerModel.Address customerAddress;
    private String productName;
    private String deliveryUnit;
    private Double shipFee;
    private String status;
    private int quantity;
    private long createdAt;

    public OrderView() {}
    
    public void setCustomerDetail(CustomerModel customer) {
        this.setCustomerName(customer.getCustomerName());
        this.setCustomerPhone(customer.getCustomerPhone());
    }
    
    public void setProductDetail(ProductModel product) {
        this.setProductName(product.getProductName());
    }
    
    public void setOrderDetail(OrderModel order) {
        this.setId(order.getId());
        this.setOrderCode(order.getOrderCode());
        this.setCustomerAddress(order.getDeliveryTo());
        this.setDeliveryUnit(order.getDeliveryUnit());
        this.setShipFee(order.getShipFee());
        this.setStatus(order.getStatus());
        this.setQuantity(order.getQuantity());
        this.setCreatedAt(order.getCreatedAt());
    }
    
    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public CustomerModel.Address getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(CustomerModel.Address customerAddress) {
        this.customerAddress = customerAddress;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

}
