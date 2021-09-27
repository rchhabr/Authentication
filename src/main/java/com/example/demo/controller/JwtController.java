package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

	@RequestMapping("/welcome")
		public String jwt() {
			String text = "This is priavte page.";
			text += "This page is not allowed to unauthorized user.";
			return text;
		}
		
}
