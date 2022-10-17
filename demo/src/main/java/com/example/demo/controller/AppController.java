package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.aop.LogExecutionTime;
import com.example.demo.repo.NoticeMessageRepository;

@RestController
@RequestMapping("/app")
public class AppController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private NoticeMessageRepository messageRepository;

	@GetMapping(path = "/test")
	@LogExecutionTime
	public String test(Principal principal, 
			@RequestParam("ids") List<Long> ids) {
//		logger.info(principal.toString());
//		JwtAuthenticationToken jwt = (JwtAuthenticationToken) principal;
//		String s  = ((org.springframework.security.oauth2.jwt.Jwt) jwt.getPrincipal()).getClaimAsString("email");
//		logger.info(s);
//		logger.info(jwt.getAuthorities().toString());
//		return principal.getName() + " : " + s;
		return "";
	}

	@GetMapping(path = "/test2")
	@PreAuthorize("hasAuthority('ROLE_EDITOR')")
	public String test2(Principal principal) {
		messageRepository.findById(4);
		return principal.getName();
	}
	
	@GetMapping(path = "/account/view")
//	@PreAuthorize("hasAuthority('VIEW_ACCOUNT')")
	public String viewAccount(Principal principal) {
		return principal.getName();
	}
	
	@GetMapping(path = "/account/{id}/edit")
	@PreAuthorize("hasAuthority('EDIT_ACCOUNT')")
	public String edit(Principal principal) {
		return principal.getName();
	}
	@PostMapping(path = "/account/{id}/view")
	public String view(Principal principal) {
		return principal.getName();
	}
}