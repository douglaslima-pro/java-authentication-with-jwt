package edu.douglaslima.jwt.authentication.security.jwt;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		// o método commence é sobrescrito a fim de tratar exceções lançadas quando o usuário não consegue se autenticar
		response.setContentType(MediaType.APPLICATION_JSON_VALUE); // Content-Type = application/json
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // status = 401
		
		final Map<String, Object> body = new HashMap<>(); // cria o corpo da resposta
		body.put("timestamp", new Date().toString());
		body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
		body.put("error", "Unauthorized");
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), body); // transforma o objeto corpo em JSON e escreve ele na resposta
	}

}
