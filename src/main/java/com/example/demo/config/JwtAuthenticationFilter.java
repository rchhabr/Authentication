package com.example.demo.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.jwtUtil.JwtUtil;
import com.example.demo.service.CustomUserDetailService;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired CustomUserDetailService customUserDetailService;
	
	@Autowired private JwtUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestTokenHeader = request.getHeader("Authorization");
		String username = null;
		
		if(requestTokenHeader!=null) {
			try {
				username = this.jwtUtil.extractUsername(requestTokenHeader);
			} catch(Exception e) {
				
			}
			UserDetails userDetails = this.customUserDetailService.loadUserByUsername(username);
		if(username!=null && SecurityContextHolder.getContext()!=null) {
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		} else {
			System.out.println("Token is not validated");
		}
		}
		filterChain.doFilter(request, response);
	}

}
