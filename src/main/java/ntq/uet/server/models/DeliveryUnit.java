package ntq.uet.server.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ntq.uet.server.exceptions.GlobalException;

@Document(collection = "deliveryUnits")
public class DeliveryUnit {
    @Id
    private String id;

    private String userId;
    private String deliveryUnitName;
    private String appId = "OMS_UET";
    private String token;
    private String shopId;
    
    public DeliveryUnit(String userId, String deliveryUnitName, String token, String shopId) {
        this.userId = userId;
        this.deliveryUnitName = deliveryUnitName;
        this.token = token;
        this.shopId = shopId;
    }

    public DeliveryUnit() {
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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeliveryUnitName() {
        return deliveryUnitName;
    }
    
    public void setDeliveryUnitName(String deliveryUnitName) {
        this.deliveryUnitName = deliveryUnitName;
    }

    public boolean validateUser(String userId) {
        return this.getUserId().equals(userId);
    }

    public void validateRequest() {
        if (this.getDeliveryUnitName() == null) throw new GlobalException("delivery unit name not null");
        if (this.getToken() == null) throw new GlobalException("delivery token not null");
    }
}
