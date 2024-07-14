package edu.douglaslima.jwt.authentication.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import edu.douglaslima.jwt.authentication.entity.User;

public class UserDetailsImpl implements UserDetails {

	private Long id;
	private String name;
	private String username;
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	private UserDetailsImpl(Long id, String name, String username, String password, String email,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
		this.authorities = authorities;
	}

	public static UserDetailsImpl fromUserEntity(User user) {
		return new UserDetailsImpl(user.getId(), user.getName(), user.getUsername(), user.getPassword(), user.getEmail(), new ArrayList<>());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}


	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
