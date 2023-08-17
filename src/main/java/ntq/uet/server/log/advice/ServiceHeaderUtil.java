package ntq.uet.server.log.advice;

import ntq.uet.server.common.core.constant.CommonConstants;
import ntq.uet.server.common.core.util.JSON;
import ntq.uet.server.common.base.ServiceHeader;
import ntq.uet.server.common.core.util.Validator;
import org.bson.internal.Base64;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ServiceHeaderUtil {

    public ServiceHeaderUtil() {
    }

    public static ServiceHeader createServiceHeader(HttpServletRequest httpServletRequest) {
        ServiceHeader serviceHeader = null;

        try {
            serviceHeader = new ServiceHeader();
            serviceHeader.setHeader(new HashMap<>());
            Object servicePath = httpServletRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
            serviceHeader.setServicePath(servicePath == null ? null : servicePath.toString());
            serviceHeader.setHttpMethod(httpServletRequest.getMethod());
            serviceHeader.setServiceMessageId(httpServletRequest.getHeader(CommonConstants.TRACE_ID));
            serviceHeader.getHeader().put(CommonConstants.HEADER_TOKEN, httpServletRequest.getHeader(CommonConstants.HEADER_TOKEN));
            extractSecurityInfo(serviceHeader);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return serviceHeader;
    }

    private static void extractSecurityInfo(ServiceHeader serviceHeader) {
        String authorization = serviceHeader.getHeader().get(CommonConstants.HEADER_TOKEN);
        if (!Validator.isEmpty(authorization) && authorization.startsWith("Bearer ")) {
            String[] chucks = authorization.split("\\.");
            if (!Validator.isEmpty(chucks)) {
                Map<String, String> claims = getJWTClaims(chucks[1]);
                if (!Validator.isEmpty(claims)) {
                    serviceHeader.setAuthenticationId((String)claims.get("jti"));
                    serviceHeader.setAuthenticationUser((String)claims.get("sub"));
                }
            }
        }
    }

    private static Map<String, String> getJWTClaims(String chuck) {
        Map<String, String> claims = null;

        try {
            byte[] decode = Base64.decode(chuck);
            claims = JSON.toLinkedHashMap(new String(decode));
        } catch (Exception var3) {
        }

        return claims;
    }
}
