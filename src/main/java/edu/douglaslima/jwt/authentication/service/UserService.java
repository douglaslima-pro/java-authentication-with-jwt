package edu.douglaslima.jwt.authentication.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.douglaslima.jwt.authentication.entity.User;
import edu.douglaslima.jwt.authentication.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public void insert(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
}
