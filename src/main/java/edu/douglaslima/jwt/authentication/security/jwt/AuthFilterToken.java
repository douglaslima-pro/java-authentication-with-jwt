package edu.douglaslima.jwt.authentication.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import edu.douglaslima.jwt.authentication.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthFilterToken extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			// primeiro obtém o token do cabeçalho da solicitação
			String token = getToken(request);
			System.out.println("DEBUG -> token = " + token);
			// depois valida o token
			if (token != null && jwtUtils.validateJwtToken(token)) {
				String username = jwtUtils.getUsername(token); // obtém o nome de usuário a partir do token
				UserDetails userDetails = userDetailsService.loadUserByUsername(username); // carrega os dados do usuário a partir do nome de usuário
				UsernamePasswordAuthenticationToken userAuthentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); // cria um objeto que armazena os detalhes do usuário
				userAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // cria uma nova instância dos detalhes de autenticação
				SecurityContextHolder.getContext().setAuthentication(userAuthentication); // atualiza as informações de autenticação, gerando um novo token
			}
		} catch (Exception e) {
			System.out.println("Ocorreu um erro ao processar o token");
		} finally {
			
		}
		filterChain.doFilter(request, response);
	}

	public String getToken(HttpServletRequest request) {
		// obtém o valor do token contido no cabeçalho "Authorization"
		String authorization = request.getHeader("Authorization");
		System.out.println("DEBUG -> Authorization = " + authorization);
		if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer")) {
			// se o cabeçalho conter texto e começar com "Bearer", retorna o token
			return authorization.replace("Bearer ", "");
		}
		// caso contrário, retorna nulo
		return null;
	}
	
}
