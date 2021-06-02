package ntq.uet.server.models;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

@Document(collection = "users")
@Getter @Setter
@AllArgsConstructor
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
    
    public void updateData(User newData) {
        this.setEmail(newData.getEmail());
        this.setName(newData.getName());
        this.setPhone(newData.getPhone());
        this.setShopName(newData.getShopName());
        this.setShopAddress(newData.getShopAddress());
    }
}
