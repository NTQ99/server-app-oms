package ntq.uet.server.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import ntq.uet.server.model.payload.request.LoginRequest;
import ntq.uet.server.model.payload.request.SignupRequest;
import ntq.uet.server.model.payload.response.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ntq.uet.server.repository.UserRepository;
import ntq.uet.server.repository.RoleRepository;

import ntq.uet.server.security.jwt.*;
import ntq.uet.server.security.services.*;
import ntq.uet.server.common.exception.GlobalException;
import ntq.uet.server.model.entity.Role;
import ntq.uet.server.model.entity.User;
import ntq.uet.server.model.payload.BasePageResponse;
import ntq.uet.server.model.payload.ErrorMessage;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping("/login")
	public ResponseEntity<BasePageResponse<JwtResponse>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());

			BasePageResponse<JwtResponse> response = new BasePageResponse<>(
					new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles), ErrorMessage.StatusCode.AUTH_SUCCESS.message);

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			if (e.getMessage().equals("Bad credentials")) {
				throw new GlobalException(ErrorMessage.StatusCode.USER_WRONG_PASSWORD.message);
			} else {
				throw new GlobalException(e.getMessage());
			}
		}
	}

	@PostMapping("/register")
	public ResponseEntity<BasePageResponse<?>> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			throw new GlobalException(ErrorMessage.StatusCode.USER_EXIST.message);
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()));

		Set<Role> roles = new HashSet<>();

		if (signUpRequest.getAdminKey() == null) {
			Role userRole = roleRepository.findByName(Role.ERole.ROLE_USER)
					.orElseThrow(() -> new GlobalException("Quyền không hợp lệ!"));
			roles.add(userRole);
		} else {
			if (signUpRequest.getAdminKey().equals("oms")) {
				Role adminRole = roleRepository.findByName(Role.ERole.ROLE_ADMIN)
						.orElseThrow(() -> new GlobalException("Quyền không hợp lệ!"));
				roles.add(adminRole);
			} else {
				throw new GlobalException("Admin Key không hợp lệ!");
			}
		}

		user.setRoles(roles);
		userRepository.save(user);

		BasePageResponse<?> response = new BasePageResponse<>(null, ErrorMessage.StatusCode.USER_CREATED.message);

		return ResponseEntity.ok(response);
	}
}
