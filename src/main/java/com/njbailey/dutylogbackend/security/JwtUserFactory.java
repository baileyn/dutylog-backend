package com.njbailey.dutylogbackend.security;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.njbailey.dutylogbackend.model.User;
import com.njbailey.dutylogbackend.model.security.Authority;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
    	List<GrantedAuthority> result = new ArrayList<>();
    	
    	for(Authority authority : authorities) {
    		result.add(new SimpleGrantedAuthority(authority.getName().name()));
    	}
    	
        return result;
    }
}
