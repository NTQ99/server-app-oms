//package ntq.uet.server.log.advice;
//
//import ntq.uet.server.common.base.ServiceHeader;
//import ntq.uet.server.common.core.constant.CommonConstants;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.http.HttpMethod;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.DispatcherType;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Component
//public class LogInterceptor implements HandlerInterceptor {
//    private static final Logger log = LogManager.getLogger(LogInterceptor.class);
//
//    public LogInterceptor() {}
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name())) {
//            ServiceHeader serviceHeader = ServiceHeaderUtil.createServiceHeader(request);
//            request.setAttribute(CommonConstants.SERVICE_HEADER, serviceHeader);
//            log.info(serviceHeader.toString());
//        }
//
//        return true;
//    }
//
//}
