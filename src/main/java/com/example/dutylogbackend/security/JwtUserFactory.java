package com.example.dutylogbackend.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.dutylogbackend.model.User;
import com.example.dutylogbackend.model.security.Authority;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class JwtUserFactory {
	public static JwtUser create(User user) {
		return new JwtUser(user.getId(), 
				user.getUsername(), 
				user.getFirstName(), 
				user.getLastName(), 
				user.getPassword(), 
				user.getEmail(), 
				user.isEnabled(), 
				user.getLastPasswordResetDate(), 
				mapToGrantedAuthorities(user.getAuthorities()));
	}
	
	private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                .collect(Collectors.toList());
    }
}
