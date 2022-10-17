package com.example.demo.controller;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Permission;
import com.example.demo.service.UserManagementService;
import com.google.firebase.auth.FirebaseAuthException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

	private final UserManagementService userManagementService;

//	@Secured("ROLE_READ")
	@PostMapping(path = "/user-claims/{uid}")
	public void setUserClaims(@PathVariable String uid, @RequestBody List<Permission> requestedClaims)
			throws FirebaseAuthException {
		userManagementService.setUserClaims(uid, requestedClaims);
	}

}