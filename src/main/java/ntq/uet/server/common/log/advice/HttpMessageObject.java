package ntq.uet.server.common.log.advice;

import ntq.uet.server.common.base.BaseObject;
import ntq.uet.server.common.base.ServiceHeader;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpMessageObject extends BaseObject {
    private String servicePath;
    private String httpMethod;
    private int responseCode;
    private Map<String, String> header = new HashMap();
    private String body = null;

    public HttpMessageObject(ServiceHeader serviceHeader, String body) {
        this.httpMethod = serviceHeader.getHttpMethod();
        this.servicePath = serviceHeader.getServicePath();
        this.header = serviceHeader.getHeader();
        this.body = body;
    }

    public HttpMessageObject(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String body) {
        this.httpMethod = httpRequest.getMethod();
        this.servicePath = httpRequest.getServletPath();
        this.responseCode = httpResponse.getStatus();
        Collection<String> headerNames = httpResponse.getHeaderNames();
        Iterator var5 = headerNames.iterator();

        while(var5.hasNext()) {
            String headerName = (String)var5.next();
            this.header.put(headerName, httpResponse.getHeader(headerName));
        }

        this.body = body;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getServicePath() {
        return this.servicePath;
    }

    public void setServicePath(String servicePath) {
        this.servicePath = servicePath;
    }

    public String getHttpMethod() {
        return this.httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Map<String, String> getHeader() {
        return this.header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
