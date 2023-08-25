package ntq.uet.server.common.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FieldViolation {
    private String field;
    private String description;
    @JsonIgnore
    Throwable throwable;

    public FieldViolation(String field, String description) {
        this(field, description, null);
    }
}
