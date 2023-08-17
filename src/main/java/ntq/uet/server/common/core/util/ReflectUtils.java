package ntq.uet.server.common.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReflectUtils {
    private static final Logger logger = LogManager.getLogger(ReflectUtils.class);

    public ReflectUtils() {
    }

    public static <E> E getFieldValue(Object obj, String fieldName) {
        Field field = getAccessibleField(obj, fieldName);
        if (field == null) {
            logger.debug("Trong [" + obj.getClass() + "] Không tìm thấy [" + fieldName + "]");
            return null;
        } else {
            E result = null;

            try {
                result = (E) field.get(obj);
            } catch (IllegalAccessException var5) {
                logger.error("Ngoại lệ ném không thể {}", var5.getMessage());
            }

            return result;
        }
    }

    public static Field getAccessibleField(Object obj, String fieldName) {
        if (obj == null) {
            return null;
        } else {
            Validate.notBlank(fieldName, "fieldName can't be blank", new Object[0]);
            Class<?> superClass = obj.getClass();

            while(superClass != Object.class) {
                try {
                    Field field = superClass.getDeclaredField(fieldName);
                    makeAccessible(field);
                    return field;
                } catch (NoSuchFieldException var4) {
                    superClass = superClass.getSuperclass();
                }
            }

            return null;
        }
    }

    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }

    }

    public static void setFieldValue(Object obj, String fieldName, String val) {
        Field field = getAccessibleField(obj, fieldName);
        if (field == null) {
            logger.debug("Trong [" + obj.getClass() + "] Không tìm thấy [" + fieldName + "]");
        }

        try {
            field.set(obj, val);
        } catch (IllegalAccessException var5) {
            logger.error("Ngoại lệ ném không thể {}", var5.getMessage());
        }

    }

    public static void setFieldValue(Object obj, String fieldName, Object val) {
        Field field = getAccessibleField(obj, fieldName);
        if (field == null) {
            logger.debug("Trong [" + obj.getClass() + "] Không tìm thấy [" + fieldName + "]");
        }

        try {
            field.set(obj, val);
        } catch (IllegalAccessException var5) {
            logger.error("Ngoại lệ ném không thể {}", var5.getMessage());
        }

    }

    private static Field getDeclaredField(Object object, String filedName) {
        Class<?> superClass = object.getClass();

        while(superClass != Object.class) {
            try {
                return superClass.getDeclaredField(filedName);
            } catch (NoSuchFieldException var4) {
                logger.error("Error {}", var4.getMessage());
                superClass = superClass.getSuperclass();
            }
        }

        return null;
    }
}
