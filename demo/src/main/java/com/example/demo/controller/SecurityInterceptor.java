package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.model.Privilege;
import com.example.demo.repo.PrivilegeRepository;

@Component
public class SecurityInterceptor implements HandlerInterceptor {
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PrivilegeRepository privilegeRepository;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		System.out.println("!!!!!!!!!!!!");
		System.out.println(request.getServletPath());
		System.out.println(request.getMethod());

		List<Privilege> privileges = privilegeRepository.findAll().stream()
				.filter(p -> p.getApi().equals(request.getServletPath())).collect(Collectors.toList());

		for (Privilege privilege : privileges) {
			if (!privilege.isSecured())
				return true;

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication instanceof JwtAuthenticationToken) {
				List<String> authorities = authentication.getAuthorities().stream().map(g-> g.getAuthority()).collect(Collectors.toList());
				System.out.println(authorities);
				
				if (authorities.contains(privilege.getName())) {
					return true;
				}
			}
		}

		throw new AccessDeniedException("Deny");
	}
}
