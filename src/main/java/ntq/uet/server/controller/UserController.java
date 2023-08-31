package ntq.uet.server.controller;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import ntq.uet.server.common.base.RequestContext;
import ntq.uet.server.common.base.ServiceHeader;
import ntq.uet.server.common.core.constant.CommonConstants;
import ntq.uet.server.common.exception.ErrorCode;
import ntq.uet.server.common.base.MetaData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import ntq.uet.server.common.exception.GlobalException;
import ntq.uet.server.model.entity.User;
import ntq.uet.server.common.base.BaseResponse;
import ntq.uet.server.service.impl.UserService;

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
	public ResponseEntity<BaseResponse<List<User>>> getAll(@RequestBody(required = false) MetaData request) {
		List<User> allUsers = new ArrayList<>();
		if (request != null) {
			Pageable paging = PageRequest.of(request.getPage() - 1, request.getPerpage());
			Page<User> pageUsers = service.getAllUsers(paging);
			allUsers = pageUsers.getContent();
			allUsers.remove(0);

			return new ResponseEntity<>(new BaseResponse<>(allUsers, pageUsers.getNumber(),
					pageUsers.getTotalPages(), pageUsers.getSize(), pageUsers.getTotalElements()), HttpStatus.OK);
		} else {
			allUsers = service.getAllUsers();
			allUsers.remove(0);
			return new ResponseEntity<>(new BaseResponse<>(allUsers, ErrorCode.OK),
					HttpStatus.OK);
		}

	}

	@PostMapping("/get/info")
	public ResponseEntity<BaseResponse<User>> getUserInfo() {

		RequestContext ctx = RequestContext.init(httpServletRequest);

        String id = ctx.getAuthenticationId();
		User userData = service.getUserById(id);
		if (userData == null) {
			throw new GlobalException(ErrorCode.USER_NOT_FOUND);
		}

		userData.setPassword(null);

		return new ResponseEntity<>(
				new BaseResponse<>(userData, ErrorCode.USER_MODIFIED), HttpStatus.OK);

	}

	@PostMapping("/update/info")
	public ResponseEntity<BaseResponse<User>> updateUserInfo(@RequestBody User newUserData) {

		RequestContext ctx = RequestContext.init(httpServletRequest);

        String id = ctx.getAuthenticationId();
		User currUserData = service.getUserById(id);
		if (currUserData == null) {
			throw new GlobalException(ErrorCode.USER_NOT_FOUND);
		}

		User newUserModified = service.updateUser(id, newUserData);
		return new ResponseEntity<>(
				new BaseResponse<>(newUserModified, ErrorCode.USER_MODIFIED), HttpStatus.OK);

	}

	@PostMapping("/update/password")
	public ResponseEntity<BaseResponse<User>> updateUserPassword(@RequestBody User newUserData) {

		RequestContext ctx = RequestContext.init(httpServletRequest);

        String id = ctx.getAuthenticationId();
		User currUserData = service.getUserById(id);
		if (currUserData == null) {
			throw new GlobalException(ErrorCode.USER_NOT_FOUND);
		}

		User newUserModified = service.updatePassword(id, encoder.encode(newUserData.getPassword()));
		return new ResponseEntity<>(
				new BaseResponse<>(newUserModified, ErrorCode.USER_MODIFIED), HttpStatus.OK);

	}

	@PostMapping("/update/status/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BaseResponse<User>> updateUserStatus(@PathVariable("id") String id, @RequestBody User newUserData) {

		User currUserData = service.getUserById(id);
		if (currUserData == null) {
			throw new GlobalException(ErrorCode.USER_NOT_FOUND);
		}

		User newUserModified = service.updateUserStatus(id, newUserData.getStatus().name());
		return new ResponseEntity<>(
				new BaseResponse<>(newUserModified, ErrorCode.USER_MODIFIED), HttpStatus.OK);

	}

	@PostMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BaseResponse<User>> deleteUser(@PathVariable("id") String id) {

		service.deleteUser(id);
		return new ResponseEntity<>(
				new BaseResponse<>(null, ErrorCode.OK), HttpStatus.OK);

	}

	@PostMapping("/delete")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BaseResponse<User>> deleteAllUsers() {

		service.deleteAllUsers();
		return new ResponseEntity<>(
				new BaseResponse<>(null, ErrorCode.OK), HttpStatus.OK);

	}

}
