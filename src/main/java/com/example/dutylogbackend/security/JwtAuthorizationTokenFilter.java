package com.example.dutylogbackend.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {
	private final UserDetailsService userDetailsService;
	private final JwtTokenUtil jwtTokenUtil;
	
	@Value("${jwt.header}")
	private String tokenHeader;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("Processing authentication for " + request.getRequestURL());
		
		final String requestHeader = request.getHeader(tokenHeader);
		
		Optional<DecodedJWT> optionalToken = Optional.empty();
		
		if(requestHeader != null && requestHeader.startsWith("Bearer ")) {
			// The authentication token is the part after 'Bearer '
			String authToken = requestHeader.substring(7);
			
			optionalToken = jwtTokenUtil.getDecodedJWT(authToken);
		}
		
		if(SecurityContextHolder.getContext().getAuthentication() == null) {
			optionalToken.ifPresent(token -> {
				UserDetails userDetails = userDetailsService.loadUserByUsername(token.getSubject());
				
				if(jwtTokenUtil.validateToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			});
		}
		
		filterChain.doFilter(request,  response);
	}
}
