package ntq.uet.server.common.log.advice;

import java.lang.reflect.Type;
import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ntq.uet.server.common.base.ServiceHeader;
import ntq.uet.server.common.core.constant.CommonConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

@ControllerAdvice
public class LogRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter implements HandlerInterceptor {
    private static final Logger log = LogManager.getLogger(LogRequestBodyAdviceAdapter.class);
    @Autowired
    private HttpServletRequest httpServletRequest;

    public LogRequestBodyAdviceAdapter() {
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

        ServiceHeader serviceHeader = (ServiceHeader) this.httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER);
        if (serviceHeader == null) {
            serviceHeader = ServiceHeaderUtil.createServiceHeader(this.httpServletRequest);
            this.httpServletRequest.setAttribute(CommonConstants.SERVICE_HEADER, serviceHeader);
        }

        HttpMessageObject httpMessageObject = new HttpMessageObject(serviceHeader, body.toString());
        log.info(httpMessageObject.toString());

        ThreadContext.put(CommonConstants.SERVICE_HEADER, serviceHeader.toString());
        ThreadContext.put(CommonConstants.TRACE_ID, serviceHeader.getServiceMessageId());

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name())) {
            ServiceHeader serviceHeader = ServiceHeaderUtil.createServiceHeader(request);
            request.setAttribute(CommonConstants.SERVICE_HEADER, serviceHeader);
            log.info(serviceHeader.toString());
        }

        return true;
    }
}
