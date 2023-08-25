package ntq.uet.server.model.payload.request;

import ntq.uet.server.common.base.BaseObject;

import java.util.Set;

import javax.validation.constraints.*;
 
public class SignupRequest extends BaseObject {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
 
    private Set<String> roles;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private String adminKey;
  
    public String getUsername() {
        return username;
    }
 
    public String getAdminKey() {
        return adminKey;
    }

    public void setAdminKey(String adminKey) {
        this.adminKey = adminKey;
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
    
    public Set<String> getRoles() {
      return this.roles;
    }
    
    public void setRole(Set<String> roles) {
      this.roles = roles;
    }
}
