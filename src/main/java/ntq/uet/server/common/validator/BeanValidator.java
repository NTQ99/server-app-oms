package ntq.uet.server.common.validator;

import ntq.uet.server.common.exception.FieldViolation;
import ntq.uet.server.common.exception.ValidateException;

import java.util.ArrayList;
import java.util.List;

public interface BeanValidator<T> {
    default void validate(T t) throws ValidateException {
        List<FieldViolation> violations = new ArrayList<>();
        validate(t, violations);
        if (!violations.isEmpty()) {
            throw new ValidateException("Invalid object " + t.getClass(), violations);
        }
    }

    void validate(T t, List<FieldViolation> violations) throws ValidateException;
}
