package edu.douglaslima.jwt.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.douglaslima.jwt.authentication.dto.AuthenticationDTO;
import edu.douglaslima.jwt.authentication.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationDTO authenticationDTO) {
		return ResponseEntity.accepted().body(authenticationService.login(authenticationDTO));	
	}
	
}
