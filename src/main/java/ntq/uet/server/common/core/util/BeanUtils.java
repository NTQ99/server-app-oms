package ntq.uet.server.common.core.util;

import org.springframework.aop.framework.AopProxyUtils;

public class BeanUtils {
    public BeanUtils() {
    }

    public static Class getTargetClass(Object instance) {
        return instance instanceof Class ? (Class) instance : AopProxyUtils.ultimateTargetClass(instance);
    }
}
