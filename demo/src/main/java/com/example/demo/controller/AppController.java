package com.example.demo.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repo.NoticeMessageRepository;

@RestController
@RequestMapping("/app")
public class AppController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private NoticeMessageRepository messageRepository;

	@GetMapping(path = "/test")
	public String test(Principal principal) {
		logger.info(principal.toString());
		JwtAuthenticationToken jwt = (JwtAuthenticationToken) principal;
		String s  = ((org.springframework.security.oauth2.jwt.Jwt) jwt.getPrincipal()).getClaimAsString("email");
		logger.info(s);
		return principal.getName() + " : " + s;
	}

	@GetMapping(path = "/test2")
	@Secured("ROLE_READ")
	public String test2(Principal principal) {
		messageRepository.findById(4);
		return principal.getName();
	}
	
}