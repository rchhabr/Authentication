package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.jwtUtil.JwtUtil;
import com.example.demo.model.JwtRequest;
import com.example.demo.model.JwtResponse;
import com.example.demo.service.CustomUserDetailService;

@RestController
public class TokenController {

	@Autowired  AuthenticationManager authenticationManager;
	
	@Autowired private CustomUserDetailService customUserDetailService;
	
	@Autowired private JwtUtil jwtUtil;
	
	@PostMapping(value = "/token")
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequestBody) throws Exception{
		
		System.out.println(jwtRequestBody);
		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequestBody.getUsername(), jwtRequestBody.getPassword()));
		} catch(UsernameNotFoundException e) {
			e.printStackTrace();
			throw new Exception("Bad Credientials");
		}
		UserDetails userDetails = customUserDetailService.loadUserByUsername(jwtRequestBody.getUsername());
		String token = this.jwtUtil.generateToken(userDetails);
		
		System.out.println(token);
		return ResponseEntity.ok(new JwtResponse(token));
		
	}
}
