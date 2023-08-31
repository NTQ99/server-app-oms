package ntq.uet.server.model.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
	private String accessToken;
	private String type = "Bearer";
	private String id;
	private String username;
	private List<String> roles;

	public JwtResponse(String accessToken, String id, String username, List<String> roles) {
		this.accessToken = accessToken;
		this.id = id;
		this.username = username;
		this.roles = roles;
	}
}
