package ntq.uet.server.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ntq.uet.server.exceptions.GlobalException;

@Document(collection = "products")
public class Product {
    @Id
    private String id;

    private String userId;
    private String productCode;
    private String productName;
    private String productDetail;
    private List<String> productPhotos;
    private int[] price = new int[3]; // 0: giá vốn, 1: giá lẻ, 2: giá sỉ
    private double promotion;
    private int weight; // đơn vị gram
    private int stock;
    private long createdAt;

    public Product() {
        long now = System.currentTimeMillis();
        this.setProductCode(String.format("%07d", now % 1046527));
        this.setCreatedAt(now);
    };

    public int[] getPrice() {
        return price;
    }

    public void setPrice(int[] price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public double getPromotion() {
        return promotion;
    }

    public void setPromotion(double promotion) {
        this.promotion = promotion;
    }

    public List<String> getProductPhotos() {
        return productPhotos;
    }

    public void setProductPhotos(List<String> productPhotos) {
        this.productPhotos = productPhotos;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public boolean validateUser(String userId) {
        return this.getUserId().equals(userId);
    }
    
    public void validateRequest() {
        if (this.getProductName() == null) throw new GlobalException("product name not null");
        if (this.getPrice() == null) throw new GlobalException("price not null");
    }
}
