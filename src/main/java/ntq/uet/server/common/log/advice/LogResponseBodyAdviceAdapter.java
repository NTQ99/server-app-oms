package ntq.uet.server.common.log.advice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class LogResponseBodyAdviceAdapter implements ResponseBodyAdvice<Object> {
    private static final Logger log = LogManager.getLogger(LogResponseBodyAdviceAdapter.class);
    @Autowired
    private HttpServletRequest httpServletRequest;

    public LogResponseBodyAdviceAdapter() {
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletResponse servletResponse = ((ServletServerHttpResponse)response).getServletResponse();
        ThreadContext.put("responseCode", "" + servletResponse.getStatus());

        HttpMessageObject httpMessageObject = new HttpMessageObject(this.httpServletRequest, servletResponse, body == null ? null : body.toString());
        log.info(httpMessageObject.toString());
        ThreadContext.clearAll();

        return body;
    }

}
