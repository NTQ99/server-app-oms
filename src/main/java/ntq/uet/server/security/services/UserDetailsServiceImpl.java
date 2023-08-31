package ntq.uet.server.security.services;

import ntq.uet.server.common.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ntq.uet.server.common.exception.GlobalException;
import ntq.uet.server.model.entity.User;
import ntq.uet.server.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new GlobalException(ErrorCode.USER_NOT_FOUND);
		}

		if (user.getStatus() == User.UserStatus.banned) {
			throw new GlobalException(ErrorCode.USER_BANNED);
		}

		if (user.getStatus() == User.UserStatus.locked) {
			throw new GlobalException(ErrorCode.USER_LOCKED);
		}

		return UserDetailsImpl.build(user);
	}

}
