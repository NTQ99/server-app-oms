package ntq.uet.server.models;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

    public enum UserStatus {
        active,
        locked,
        banned
    }

    @Id
    private String id;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private UserStatus status;

    @DBRef
    private Set<Role> roles = new HashSet<>();  

    private String email;
    private String name;
    private String phone;
    private String shopName;
    private Address shopAddress;
    private long createdAt;

    public User() {
        long now = System.currentTimeMillis();
        this.setCreatedAt(now);
        this.setStatus(UserStatus.active);
    }

    public User(String username, String password) {
        long now = System.currentTimeMillis();
        this.setCreatedAt(now);
        this.setUsername(username);
        this.setPassword(password);
        this.setStatus(UserStatus.active);
    }
    
    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public Address getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(Address shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void updateData(User newData) {
        this.setEmail(newData.getEmail());
        this.setName(newData.getName());
        this.setPhone(newData.getPhone());
        this.setShopName(newData.getShopName());
        this.setShopAddress(newData.getShopAddress());
    }
}
