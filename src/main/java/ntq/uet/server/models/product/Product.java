package ntq.uet.server.models.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product {
    @Id
    private String id;

    private String productCode;
    private String productName = "";
    private String productDetail = "";
    private List<String> productPhotos = new ArrayList<>();
    private Double capitalPrice = 0.0;
    private Double retailPrice = 0.0;
    private Double wholesalePrice = 0.0;
    private Double promotion = 0.0;
    private Double weight = 0.0;
    private long createdAt;

    public Product() {
        long now = System.currentTimeMillis();
        this.setProductCode(String.format("%07d", now % 1046527));
        this.setCreatedAt(now);
    };

    public Product(String productName, String productDetail, List<String> productPhotos, Double retailPrice,
            Double wholesalePrice, Double promotion) {
        long now = System.currentTimeMillis();
        this.setProductCode(String.format("%07d", now % 1046527));
        this.setCreatedAt(now);
        this.setProductName(productName);
        this.setProductDetail(productDetail);
        this.setProductPhotos(productPhotos);
        this.setRetailPrice(retailPrice);
        this.setWholesalePrice(wholesalePrice);
        this.setPromotion(promotion);
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

    public Double getPromotion() {
        return promotion;
    }

    public void setPromotion(Double promotion) {
        this.promotion = promotion;
    }

    public Double getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(Double wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public Double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Product product = (Product) o;

        return this.getProductName().equals(product.getProductName())
                && this.getProductDetail().equals(product.getProductDetail())
                && this.getProductPhotos().equals(product.getProductPhotos())
                && this.getPromotion().equals(product.getPromotion())
                && this.getRetailPrice().equals(product.getRetailPrice())
                && this.getWholesalePrice().equals(product.getWholesalePrice());
    }
}
