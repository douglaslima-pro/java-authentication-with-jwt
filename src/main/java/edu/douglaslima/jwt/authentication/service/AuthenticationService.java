package edu.douglaslima.jwt.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import edu.douglaslima.jwt.authentication.dto.AccessDTO;
import edu.douglaslima.jwt.authentication.dto.AuthenticationDTO;
import edu.douglaslima.jwt.authentication.security.jwt.JwtUtils;

@Service
public class AuthenticationService {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	public AccessDTO login(AuthenticationDTO authenticationDTO) {
		try {
			// armazena as credenciais do usuário
			UsernamePasswordAuthenticationToken userAuthentication = new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(), authenticationDTO.getPassword());
			// autentica o usuário e retorna um objeto do tipo Authentication
			Authentication authentication = authenticationManager.authenticate(userAuthentication);
			// retorna as informações do usuário logado
			UserDetailsImpl userAuthenticated = (UserDetailsImpl) authentication.getPrincipal();
			String token = jwtUtils.generateTokenFromUserDetails(userAuthenticated);
			AccessDTO accessDTO = new AccessDTO(token);
			return accessDTO;
		} catch(BadCredentialsException e) {
			System.out.println("Credenciais incorretas! " + e.getMessage());
		}
		System.out.println("ERRO -> AUTENTICAÇÃO FALHOU! OBSERVAR OS LOGS!");
		return new AccessDTO(null);
	}
	
}
