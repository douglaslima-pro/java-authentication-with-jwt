package edu.douglaslima.jwt.authentication.security.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import edu.douglaslima.jwt.authentication.service.UserDetailsImpl;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

	@Value("${projeto.jwt.secret}")
	private String jwtSecret;
	
	@Value("${projeto.jwt.expiration-ms}")
	private int jwtExpirationMs;

	public String generateTokenFromUserDetails(UserDetailsImpl userDetails) {
		Date issuedAt = new Date();
		return Jwts.builder()
				.subject(userDetails.getUsername()) // nome de usuário
				.issuedAt(issuedAt) // data de criação do token JWT
				.expiration(new Date(issuedAt.getTime() + jwtExpirationMs)) // data de expiração
				.signWith(getSigningKey()) // assina o token JWT utilizando uma chave gerada com um algoritmo de assinatura
				.compact();
	}
	
	public SecretKey getSigningKey() {
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)); // cria uma nova chave a partir de uma sequência de bytes, utilizando um algoritmo HMAC-SHA
		return key;
	}
	
	public String getUsername(String token) {
		String username = Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
		System.out.println("DEBUG -> username = " + username);
		return username;
	}
	
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser() // cria um JwtParserBuilder que constrói um JwtParser, útil para ler uma string que representa um token JWT e converter para um objeto do tipo JWT
				.verifyWith(getSigningKey()) // utiliza a chave para verificar a assinatura do token JWT
				.build() // constrói o objeto JwtParser
				.parseSignedClaims(authToken); // 
			return true;
		} catch (UnsupportedJwtException e) {
			System.out.println("UnsupportedJwtException: Token não suportado");
		} catch (JwtException e) {
			System.out.println("JwtException: Token inválido");
		} catch (IllegalArgumentException e) {
			System.out.println("IllegalArgumentException: Token nulo ou vazio");
		}
		return false;
	}
	
}
