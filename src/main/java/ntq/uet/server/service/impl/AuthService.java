package ntq.uet.server.service.impl;

import lombok.RequiredArgsConstructor;
import ntq.uet.server.common.base.BaseResponse;
import ntq.uet.server.common.base.RequestContext;
import ntq.uet.server.common.exception.ErrorCode;
import ntq.uet.server.common.exception.GlobalException;
import ntq.uet.server.model.entity.Role;
import ntq.uet.server.model.entity.User;
import ntq.uet.server.model.payload.request.LoginRequest;
import ntq.uet.server.model.payload.request.SignupRequest;
import ntq.uet.server.model.payload.response.JwtResponse;
import ntq.uet.server.repository.RoleRepository;
import ntq.uet.server.repository.UserRepository;
import ntq.uet.server.security.jwt.JwtUtils;
import ntq.uet.server.security.services.UserDetailsImpl;
import ntq.uet.server.service.IAuthService;
import ntq.uet.server.util.Constants;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    @Override
    public BaseResponse<JwtResponse> authenticateUser(RequestContext ctx) {
        LoginRequest request = (LoginRequest) ctx.getRequestData();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return new BaseResponse<>(
                    new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles), ErrorCode.AUTH_SUCCESS);

        } catch (Exception e) {
            if (e.getMessage().equals("Bad credentials")) {
                throw new GlobalException(ErrorCode.USER_WRONG_PASSWORD);
            } else {
                throw new GlobalException(e.getMessage());
            }
        }
    }

    @Override
    public BaseResponse<?> registerUser(RequestContext ctx) {
        SignupRequest request = (SignupRequest) ctx.getRequestData();

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new GlobalException(ErrorCode.USER_EXIST);
        }

        // Create new user's account
        User user = new User(request.getUsername(), encoder.encode(request.getPassword()));

        Set<Role> roles = new HashSet<>();

        if (request.getAdminKey() == null) {
            Role userRole = roleRepository.findByName(Role.ERole.ROLE_USER)
                    .orElseThrow(() -> new GlobalException("Quyền không hợp lệ!"));
            roles.add(userRole);
        } else {
            if (Constants.ADMIN_USER_KEY.equals(request.getAdminKey())) {
                Role adminRole = roleRepository.findByName(Role.ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new GlobalException("Quyền không hợp lệ!"));
                roles.add(adminRole);
            } else {
                throw new GlobalException("Admin Key không hợp lệ!");
            }
        }

        user.setRoles(roles);
        userRepository.save(user);

        return new BaseResponse<>(null, ErrorCode.USER_CREATED);
    }
}
