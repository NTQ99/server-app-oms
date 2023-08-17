package ntq.uet.server.common.base;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;

public class ServiceHeader extends BaseObject {
    private String servicePath;
    private String httpMethod;
    private String serviceMessageId;
    private String authorization;
    private String authenticationId;
    private String authenticationUser;
    @JsonIgnore
    private Map<String, String> header;

    public ServiceHeader() {
    }

    public ServiceHeader(String servicePath, String httpMethod, String serviceMessageId, String authorization, String authenticationId, String authenticationUser, Map<String, String> header) {
        this.servicePath = servicePath;
        this.httpMethod = httpMethod;
        this.serviceMessageId = serviceMessageId;
        this.authorization = authorization;
        this.authenticationId = authenticationId;
        this.authenticationUser = authenticationUser;
        this.header = header;
    }

    public String getServicePath() {
        return servicePath;
    }

    public void setServicePath(String servicePath) {
        this.servicePath = servicePath;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getServiceMessageId() {
        return serviceMessageId;
    }

    public void setServiceMessageId(String serviceMessageId) {
        this.serviceMessageId = serviceMessageId;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getAuthenticationUser() {
        return authenticationUser;
    }

    public void setAuthenticationUser(String authenticationUser) {
        this.authenticationUser = authenticationUser;
    }

    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
    }
}
