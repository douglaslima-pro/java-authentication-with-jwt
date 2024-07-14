package edu.douglaslima.jwt.authentication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import edu.douglaslima.jwt.authentication.security.jwt.AuthEntryPointJwt;
import edu.douglaslima.jwt.authentication.security.jwt.AuthFilterToken;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	
	@Bean
	public AuthFilterToken authFilterToken() {
		return new AuthFilterToken();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors(Customizer.withDefaults())
			.csrf(csrf -> csrf.disable())
			.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(unauthorizedHandler))
			.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
					.requestMatchers("/user/insert").permitAll()
					.requestMatchers("/auth/login").permitAll()
					.anyRequest().authenticated())
			.addFilterBefore(authFilterToken(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
}
