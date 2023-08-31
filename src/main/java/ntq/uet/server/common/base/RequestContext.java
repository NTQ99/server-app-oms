package ntq.uet.server.common.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntq.uet.server.common.core.constant.CommonConstants;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestContext {
    private ServiceHeader serviceHeader;
    private Object requestData;
    private Pageable pageable;

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

    public static RequestContext init(HttpServletRequest httpServletRequest) {
        RequestContext context = new RequestContext();
        context.setServiceHeader((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));
        return context;
    }

    public static RequestContext init(HttpServletRequest httpServletRequest, Pageable pageable) {
        RequestContext context = new RequestContext();
        context.setServiceHeader((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));
        context.setPageable(pageable);
        return context;
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
}
