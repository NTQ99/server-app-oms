package ntq.uet.server.log.config;

import ntq.uet.server.log.advice.LogRequestBodyAdviceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LogRequestBodyAdviceAdapter logInterceptor;

    public WebMvcConfig() {
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.logInterceptor).excludePathPatterns(new String[]{"/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs", "/v3/api-docs", "/actuator/**", "/webjars/**", "/error", "/"}).addPathPatterns(new String[]{"/**"});
    }
}
