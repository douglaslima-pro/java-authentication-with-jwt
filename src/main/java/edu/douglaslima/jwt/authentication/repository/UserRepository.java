package edu.douglaslima.jwt.authentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.douglaslima.jwt.authentication.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);

}
