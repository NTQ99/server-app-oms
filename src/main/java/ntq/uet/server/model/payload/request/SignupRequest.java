package ntq.uet.server.model.payload.request;

import lombok.Getter;
import ntq.uet.server.common.base.BaseObject;

import java.util.Set;

import javax.validation.constraints.*;

@Getter
public class SignupRequest extends BaseObject {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    private Set<String> roles;
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    private String adminKey;
}
