package ntq.uet.server.common.core.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class JSON {
    private static final Logger logger = LogManager.getLogger(JSON.class);
    private static ObjectMapper objectMapper;

    public JSON() {
    }

    public static ObjectMapper getObjectMapper() {
        if (objectMapper != null) {
            return objectMapper;
        } else {
            objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(Include.NON_NULL);
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            return objectMapper;
        }
    }

    public static CollectionType getCollectionType(Class<?> clazzz) {
        return getObjectMapper().getTypeFactory().constructCollectionType(ArrayList.class, clazzz);
    }

    public static String stringify(Object object) {
        return toJSONString(object);
    }

    public static String toJSONString(Object model) {
        try {
            String json = getObjectMapper().writeValueAsString(model);
            return json;
        } catch (Exception var2) {
            logger.error("Java to Json exception", var2);
            return null;
        }
    }

    public static <T> T toJavaObject(String jsonStr, Class<T> clazz) {
        try {
            T model = getObjectMapper().readValue(jsonStr, clazz);
            return model;
        } catch (Exception var3) {
            logger.error("Json to Java exception", var3);
            return null;
        }
    }

    public static Map<String, Object> parseObject(String jsonStr) {
        try {
            JavaType javaType = getObjectMapper().getTypeFactory().constructParametricType(Map.class, new Class[]{String.class, Object.class});
            return (Map)getObjectMapper().readValue(jsonStr, javaType);
        } catch (Exception var2) {
            logger.error("Json to Java exception", var2);
            return null;
        }
    }

    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        return toJavaObject(jsonStr, clazz);
    }

    public static <T> T parseObject(String jsonStr, TypeReference<T> typeReference) {
        try {
            T model = getObjectMapper().readValue(jsonStr, typeReference);
            return model;
        } catch (Exception var3) {
            logger.error("Json to Java exception", var3);
            return null;
        }
    }

    public static <T> List<T> parseArray(String jsonStr, Class<T> clazz) {
        try {
            JavaType javaType = getObjectMapper().getTypeFactory().constructParametricType(List.class, new Class[]{clazz});
            return (List)getObjectMapper().readValue(jsonStr, javaType);
        } catch (Exception var3) {
            logger.error("Json to Java exception", var3);
            return null;
        }
    }

    public static Map toMap(String jsonStr) {
        return (Map)toJavaObject(jsonStr, Map.class);
    }

    public static LinkedHashMap toLinkedHashMap(String jsonStr) {
        return Validator.isEmpty(jsonStr) ? null : (LinkedHashMap)toJavaObject(jsonStr, LinkedHashMap.class);
    }

    public static byte[] toJsonBytes(Object obj) {
        if (obj == null) {
            return "{}".getBytes(Charset.forName("UTF-8"));
        } else {
            try {
                return getObjectMapper().writeValueAsBytes(obj);
            } catch (Exception var2) {
                logger.error("Json to bytes exception", var2);
                return null;
            }
        }
    }

    public static JSONObject optJSONObject(JSONObject object, Iterator<String> iterator) {
        if (object != null) {
            return iterator.hasNext() ? optJSONObject(object.optJSONObject((String)iterator.next()), iterator) : object;
        } else {
            return null;
        }
    }

    public static String toJsonJAXBE(Object object) {
        if (object == null) {
            return "null";
        } else {
            ObjectMapper mapper = new ObjectMapper();
            mapper.addMixInAnnotations(JAXBElement.class, JAXBElementMixin.class);
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            String jsonString = "";

            try {
                jsonString = mapper.writeValueAsString(object);
            } catch (JsonProcessingException var4) {
                jsonString = "Can't build json from object";
            }

            return jsonString;
        }
    }
}
