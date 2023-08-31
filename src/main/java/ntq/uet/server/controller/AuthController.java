package ntq.uet.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import ntq.uet.server.common.base.RequestContext;
import ntq.uet.server.common.base.ServiceHeader;
import ntq.uet.server.common.core.constant.CommonConstants;
import ntq.uet.server.model.payload.request.LoginRequest;
import ntq.uet.server.model.payload.request.SignupRequest;
import ntq.uet.server.service.IAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final IAuthService service;
	private final HttpServletRequest httpServletRequest;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		RequestContext ctx = RequestContext.init(httpServletRequest);
		ctx.setRequestData(loginRequest);

		return ResponseEntity.ok(service.authenticateUser(ctx));
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		RequestContext ctx = RequestContext.init(httpServletRequest);
		ctx.setRequestData(signUpRequest);

		return ResponseEntity.ok(service.registerUser(ctx));
	}
}
