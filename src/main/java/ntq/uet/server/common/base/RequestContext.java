package ntq.uet.server.common.base;

public class RequestContext {
    private ServiceHeader serviceHeader;
    private Object requestData;

    public static RequestContext init() {
        RequestContext context = new RequestContext();
        context.setServiceHeader(new ServiceHeader());
        return context;
    }

    public static RequestContext init(ServiceHeader serviceHeader) {
        RequestContext context = new RequestContext();
        context.setServiceHeader(serviceHeader);
        return context;
    }

    public void setServiceHeader(ServiceHeader serviceHeader) {
        this.serviceHeader = serviceHeader;
    }

    public void setRequestData(Object requestData) {
        this.requestData = requestData;
    }

    public Object getRequestData() {
        return this.requestData;
    }

    public ServiceHeader getServiceHeader() {
        return this.serviceHeader;
    }

    public String getServiceMessageId() {
        return this.serviceHeader == null ? null : this.serviceHeader.getServiceMessageId();
    }

    public String getServicePath() {
        return this.serviceHeader == null ? null : this.serviceHeader.getServicePath();
    }

    public String getAuthorization() {
        return this.serviceHeader == null ? null  : this.serviceHeader.getAuthorization();
    }

    public String getAuthenticationUser() {
        return this.serviceHeader == null ? null : this.serviceHeader.getAuthenticationUser();
    }

    public String getAuthenticationId() {
        return this.serviceHeader == null ? null : this.serviceHeader.getAuthenticationId();
    }

    public RequestContext() {
    }

    public RequestContext(ServiceHeader serviceHeader, Object requestData) {
        this.serviceHeader = serviceHeader;
        this.requestData = requestData;
    }
}
