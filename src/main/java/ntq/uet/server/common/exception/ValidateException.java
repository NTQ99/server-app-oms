package ntq.uet.server.common.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidateException extends RuntimeException {

    private final List<FieldViolation> fieldViolations;

    public ValidateException(String msg) {
        this(msg, null);
    }

    public ValidateException(String msg, List<FieldViolation> fieldViolations) {
        super(msg);
        this.fieldViolations = fieldViolations;
        fieldViolations.forEach(fv -> {
            if (fv.getThrowable() != null) {
                addSuppressed(fv.getThrowable());
            }
        });
    }
}
