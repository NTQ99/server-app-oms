package ntq.uet.server.common.core.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.*;

public class Validator {
    private static final Logger log = LogManager.getLogger(Validator.class);
    private static javax.validation.Validator VALIDATOR = null;
    private static final Set<String> TRUE_SET = new HashSet() {
        {
            this.add("true");
            this.add("y");
            this.add("yes");
            this.add("1");
        }
    };
    private static final Set<String> FALSE_SET = new HashSet() {
        {
            this.add("false");
            this.add("n");
            this.add("no");
            this.add("0");
        }
    };

    public Validator() {
    }

    public static boolean isEmpty(Object obj) {
        if (obj instanceof String) {
            return isEmpty((String)obj);
        } else if (obj instanceof Collection) {
            return isEmpty((Collection)obj);
        } else if (obj instanceof Map) {
            return isEmpty((Map)obj);
        } else if (obj instanceof String[]) {
            return isEmpty((String[])obj);
        } else {
            return obj == null;
        }
    }

    public static boolean isEmpty(String value) {
        return StrUtil.isBlank(value);
    }

    public static boolean isEmpty(String[] values) {
        return values == null || values.length == 0;
    }

    public static <T> boolean isEmpty(Collection<T> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isEmpty(Map obj) {
        return obj == null || obj.isEmpty();
    }

    public static boolean notEmpty(Object obj) {
        if (obj instanceof String) {
            return notEmpty((String)obj);
        } else if (obj instanceof Collection) {
            return notEmpty((Collection)obj);
        } else if (obj instanceof Map) {
            return notEmpty((Map)obj);
        } else if (obj instanceof String[]) {
            return notEmpty((String[])obj);
        } else {
            return obj != null;
        }
    }

    public static boolean notEmpty(String value) {
        return StrUtil.isNotBlank(value);
    }

    public static boolean notEmpty(String[] values) {
        return values != null && values.length > 0;
    }

    public static <T> boolean notEmpty(Collection<T> list) {
        return list != null && !list.isEmpty();
    }

    public static boolean notEmptyOrZero(Long longObj) {
        return longObj != null && longObj != 0L;
    }

    public static boolean notEmptyOrZero(Integer intObj) {
        return intObj != null && intObj != 0;
    }

    public static boolean notEmpty(Map obj) {
        return obj != null && !obj.isEmpty();
    }

    public static boolean isNumber(String str) {
        String regex = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";
        return str.matches(regex);
    }

    public static boolean isEmail(String str) {
        return isEmpty(str) ? false : str.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
    }

    public static boolean isValidBoolean(String value) {
        if (value == null) {
            return false;
        } else {
            value = StrUtil.trim(value).toLowerCase();
            return TRUE_SET.contains(value) || FALSE_SET.contains(value);
        }
    }

    public static boolean isTrue(String value) {
        if (value == null) {
            return false;
        } else {
            value = StrUtil.trim(value).toLowerCase();
            return TRUE_SET.contains(value);
        }
    }

    public static boolean notEquals(Object source, Object target) {
        return !equals(source, target);
    }

    public static <T> boolean equals(T source, T target) {
        if (source == null && target == null) {
            return true;
        } else if (source != null && target != null) {
            if (source instanceof Comparable) {
                return source.equals(target);
            } else {
                Iterator var4;
                Object key;
                if (source instanceof Collection) {
                    Collection sourceList = (Collection)source;
                    Collection targetList = (Collection)target;
                    if (sourceList.size() != targetList.size()) {
                        return false;
                    } else {
                        var4 = sourceList.iterator();

                        do {
                            if (!var4.hasNext()) {
                                var4 = targetList.iterator();

                                do {
                                    if (!var4.hasNext()) {
                                        return true;
                                    }

                                    key = var4.next();
                                } while(sourceList.contains(key));

                                return false;
                            }

                            key = var4.next();
                        } while(targetList.contains(key));

                        return false;
                    }
                } else if (source instanceof Map) {
                    Map sourceMap = (Map)source;
                    Map targetMap = (Map)target;
                    if (isEmpty(sourceMap) && isEmpty(targetMap)) {
                        return true;
                    } else if (sourceMap.size() != targetMap.size()) {
                        return false;
                    } else {
                        var4 = sourceMap.keySet().iterator();

                        Object value;
                        Object targetValue;
                        do {
                            if (!var4.hasNext()) {
                                return true;
                            }

                            key = var4.next();
                            value = sourceMap.get(key);
                            targetValue = targetMap.get(key);
                        } while(!notEquals(value, targetValue));

                        return false;
                    }
                } else {
                    log.warn("Not yet implemented type " + source.getClass().getSimpleName() + "-" + target.getClass().getSimpleName() + " the comparison!");
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public static String getBindingError(BindingResult result) {
        if (result != null && result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            List<String> allErrors = new ArrayList(errors.size());
            Iterator var3 = errors.iterator();

            while(var3.hasNext()) {
                ObjectError error = (ObjectError)var3.next();
                allErrors.add(error.getDefaultMessage().replaceAll("\"", "'"));
            }

            return StrUtil.join(allErrors);
        } else {
            return null;
        }
    }

    public static <T> String validateBean(T obj) {
        if (VALIDATOR == null) {
            VALIDATOR = ((HibernateValidatorConfiguration)((HibernateValidatorConfiguration) Validation.byProvider(HibernateValidator.class).configure()).failFast(false)).buildValidatorFactory().getValidator();
        }

        Set<ConstraintViolation<T>> errors = VALIDATOR.validate(obj, new Class[0]);
        if (errors != null && errors.size() != 0) {
            List<String> allErrors = new ArrayList(errors.size());
            Iterator var3 = errors.iterator();

            while(var3.hasNext()) {
                ConstraintViolation<T> err = (ConstraintViolation)var3.next();
                allErrors.add(err.getMessage());
            }

            return StrUtil.join(allErrors);
        } else {
            return null;
        }
    }
}
