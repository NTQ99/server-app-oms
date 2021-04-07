package ntq.uet.server.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class ProductModel {
    @Id
    private String id;

    private String productCode;
    private String productName;
    private String productDetail;
    private String [] productPhotos;
    private Double retailPrice;
    private Double wholesalePrice;
    private Double promotion;
    private Date createdDate;

    public ProductModel(){
        long now = System.currentTimeMillis();
        this.setProductCode(String.valueOf(now));
        this.setCreatedDate(new Date(now));
    };
    public ProductModel(String productName, String productDetail, String[] productPhotos, Double retailPrice,
            Double wholesalePrice, Double promotion) {
        long now = System.currentTimeMillis();
        this.setProductCode(String.valueOf(now));
        this.setCreatedDate(new Date(now));
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

    public Date getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public String [] getProductPhotos() {
        return productPhotos;
    }
    public void setProductPhotos(String [] productPhotos) {
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
}
