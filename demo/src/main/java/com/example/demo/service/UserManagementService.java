package com.example.demo.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.model.Permission;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserManagementService {

	private final FirebaseAuth firebaseAuth;

	public void setUserClaims(String uid, List<Permission> requestedPermissions) throws FirebaseAuthException {
		List<String> permissions = requestedPermissions.stream().map(Enum::toString).collect(Collectors.toList());

		Map<String, Object> claims = Map.of("custom_claims", permissions);

		firebaseAuth.setCustomUserClaims(uid, claims);
	}
}