package ntq.uet.server.model.payload.request;

import lombok.Getter;
import ntq.uet.server.common.base.BaseObject;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginRequest extends BaseObject {

	@NotBlank
	private String username;
	@NotBlank
	private String password;
}
