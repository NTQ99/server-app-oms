package ntq.uet.server.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ntq.uet.server.exceptions.GlobalException;

@Document(collection = "orders")
public class Order {

    public static class Item {
        private String productId;
        private String productName;
        private int quantity;
        public Item(String productId, String productName, int quantity) {
            this.productId = productId;
            this.productName = productName;
            this.quantity = quantity;
        }
        public int getQuantity() {
            return quantity;
        }
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
        public Item() {
        }
        public String getProductId() {
            return productId;
        }
        public String getProductName() {
            return productName;
        }
        public void setProductName(String productName) {
            this.productName = productName;
        }
        public void setProductId(String productId) {
            this.productId = productId;
        }
    }

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

    private String userId;
    private String orderCode;
    private String customerId;
    private String customerName;
    private String customerPhone;
    private List<Item> products;
    private String deliveryUnitName;
    private String deliveryCode;
    private String note;
    private Address deliveryTo;
    private int shipFee;
    private int totalPrice;
    private int codAmount;
    private boolean isPrinted;
    private Status status;
    private int priceType; // 0: giá vốn, 1: giá lẻ, 2: giá sỉ
    private long createdAt;
    private long lastModifiedAt;

    public Order() {
        long now = System.currentTimeMillis();
        this.setOrderCode(String.format("%07d", now % 1046527));
        this.setCreatedAt(now);
        this.setStatus(Status.wait_confirm);
        this.setPrinted(false);
    }

    public boolean isPrinted() {
        return isPrinted;
    }

    public void setPrinted(boolean isPrinted) {
        this.isPrinted = isPrinted;
    }

    public int getCodAmount() {
        return codAmount;
    }

    public void setCodAmount(int codAmount) {
        this.codAmount = codAmount;
    }

    public int getPriceType() {
        return priceType;
    }

    public void setPriceType(int priceType) {
        this.priceType = priceType;
    }

    public long getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(long lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Item> getProducts() {
        return products;
    }

    public void setProducts(List<Item> products) {
        this.products = products;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeliveryCode() {
        return deliveryCode;
    }

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getId() {
        return id;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getShipFee() {
        return shipFee;
    }

    public void setShipFee(int shipFee) {
        this.shipFee = shipFee;
    }

    public String getDeliveryUnitName() {
        return deliveryUnitName;
    }

    public void setDeliveryUnitName(String deliveryUnitName) {
        this.deliveryUnitName = deliveryUnitName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public boolean validateUser(String userId) {
        return this.getUserId().equals(userId);
    }

    public void validateRequest() {
        if (this.getCustomerPhone() == null) throw new GlobalException("customer name not null");
        if (this.getProducts() == null) throw new GlobalException("products not null");
    }
}
