package edu.douglaslima.jwt.authentication.dto;

public class AccessDTO {
	
	// TODO implementar retornar o usuário e as authorities
	
	private String token;
	
	public AccessDTO(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
