package edu.douglaslima.jwt.authentication.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.douglaslima.jwt.authentication.entity.User;
import edu.douglaslima.jwt.authentication.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsername(username);
		return UserDetailsImpl.fromUserEntity(user.orElseThrow(() -> new UsernameNotFoundException("Username does not exist.")));
	}

}
