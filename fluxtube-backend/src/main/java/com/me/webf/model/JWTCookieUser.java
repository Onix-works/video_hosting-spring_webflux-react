package com.me.webf.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document
public class JWTCookieUser extends AbstractUser implements UserDetails {
	
	@NonNull
	private String username;
	@NonNull
	private String password;
	private Boolean enabled;
	private List<Role> roles;
	private String avatarId;
	
	public JWTCookieUser(String username) {
		super();
		this.username = username;
	}
	
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return false;
	}
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return false;
	}
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}
	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream().map(authority -> new SimpleGrantedAuthority(authority.name()))
				.collect(Collectors.toList());
	}
	
	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}
	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	@JsonIgnore
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@JsonIgnore
	public List<Role> getRoles() {
		return roles;
	}


	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}


	public String getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}	
}
