package ntq.uet.server.controllers;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import ntq.uet.server.common.base.RequestContext;
import ntq.uet.server.common.base.ServiceHeader;
import ntq.uet.server.common.core.constant.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import ntq.uet.server.exceptions.GlobalException;
import ntq.uet.server.models.User;
import ntq.uet.server.payload.BasePageResponse;
import ntq.uet.server.payload.BaseRequest;
import ntq.uet.server.payload.ErrorMessage;
import ntq.uet.server.security.jwt.JwtUtils;
import ntq.uet.server.services.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService service;
	private final PasswordEncoder encoder;
	private final HttpServletRequest httpServletRequest;

	@PostMapping("/get")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BasePageResponse<List<User>>> getAll(@RequestBody(required = false) BaseRequest request) {
		List<User> allUsers = new ArrayList<>();
		if (request != null) {
			Pageable paging = PageRequest.of(request.getPage() - 1, request.getPerpage());
			Page<User> pageUsers = service.getAllUsers(paging);
			allUsers = pageUsers.getContent();
			allUsers.remove(0);

			return new ResponseEntity<>(new BasePageResponse<>(allUsers, pageUsers.getNumber(),
					pageUsers.getTotalPages(), pageUsers.getSize(), pageUsers.getTotalElements()), HttpStatus.OK);
		} else {
			allUsers = service.getAllUsers();
			allUsers.remove(0);
			return new ResponseEntity<>(new BasePageResponse<>(allUsers, ErrorMessage.StatusCode.OK.message),
					HttpStatus.OK);
		}

	}

	@PostMapping("/get/info")
	public ResponseEntity<BasePageResponse<User>> getUserInfo() {

		RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String id = ctx.getAuthenticationId();
		User userData = service.getUserById(id);
		if (userData == null) {
			throw new GlobalException(ErrorMessage.StatusCode.USER_NOT_FOUND.message);
		}

		userData.setPassword(null);

		return new ResponseEntity<>(
				new BasePageResponse<>(userData, ErrorMessage.StatusCode.USER_MODIFIED.message), HttpStatus.OK);

	}

	@PostMapping("/update/info")
	public ResponseEntity<BasePageResponse<User>> updateUserInfo(@RequestBody User newUserData) {

		RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String id = ctx.getAuthenticationId();
		User currUserData = service.getUserById(id);
		if (currUserData == null) {
			throw new GlobalException(ErrorMessage.StatusCode.USER_NOT_FOUND.message);
		}

		User newUserModified = service.updateUser(id, newUserData);
		return new ResponseEntity<>(
				new BasePageResponse<>(newUserModified, ErrorMessage.StatusCode.USER_MODIFIED.message), HttpStatus.OK);

	}

	@PostMapping("/update/password")
	public ResponseEntity<BasePageResponse<User>> updateUserPassword(@RequestBody User newUserData) {

		RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String id = ctx.getAuthenticationId();
		User currUserData = service.getUserById(id);
		if (currUserData == null) {
			throw new GlobalException(ErrorMessage.StatusCode.USER_NOT_FOUND.message);
		}

		User newUserModified = service.updatePassword(id, encoder.encode(newUserData.getPassword()));
		return new ResponseEntity<>(
				new BasePageResponse<>(newUserModified, ErrorMessage.StatusCode.USER_MODIFIED.message), HttpStatus.OK);

	}

	@PostMapping("/update/status/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BasePageResponse<User>> updateUserStatus(@PathVariable("id") String id, @RequestBody User newUserData) {

		User currUserData = service.getUserById(id);
		if (currUserData == null) {
			throw new GlobalException(ErrorMessage.StatusCode.USER_NOT_FOUND.message);
		}

		User newUserModified = service.updateUserStatus(id, newUserData.getStatus().name());
		return new ResponseEntity<>(
				new BasePageResponse<>(newUserModified, ErrorMessage.StatusCode.USER_MODIFIED.message), HttpStatus.OK);

	}

	@PostMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BasePageResponse<User>> deleteUser(@PathVariable("id") String id) {

		service.deleteUser(id);
		return new ResponseEntity<>(
				new BasePageResponse<>(null, ErrorMessage.StatusCode.OK.message), HttpStatus.OK);

	}

	@PostMapping("/delete")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BasePageResponse<User>> deleteAllUsers() {

		service.deleteAllUsers();
		return new ResponseEntity<>(
				new BasePageResponse<>(null, ErrorMessage.StatusCode.OK.message), HttpStatus.OK);

	}

}
