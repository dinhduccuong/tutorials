package com.example.demo.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Service;

import com.example.demo.model.Company;
import com.example.demo.model.NoticeMessage;
import com.example.demo.model.Permission;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserManagementService {

	private final FirebaseAuth firebaseAuth;

	@Autowired
	JdbcMutableAclService aclService;

	public void setUserClaims(String uid, List<Permission> requestedPermissions) throws FirebaseAuthException {
		List<String> permissions = requestedPermissions.stream().map(Enum::toString).collect(Collectors.toList());
		
		Map<String, Object> claims = Map.of("roles", permissions, "id", 1L);
		firebaseAuth.setCustomUserClaims(uid, claims);
//		setACL();
	}

	@org.springframework.transaction.annotation.Transactional
	public void setACL() {
		ObjectIdentity oi = new ObjectIdentityImpl(Company.class,1L);
		Sid sid = new PrincipalSid("test");
		Sid sid2 = new GrantedAuthoritySid("ROLE_WRITE");

		// Create or update the relevant ACL
		MutableAcl acl = null;
		try {
			acl = (MutableAcl) aclService.readAclById(oi);
		} catch (NotFoundException nfe) {
			acl = aclService.createAcl(oi);
		}

		// Now grant some permissions via an access control entry (ACE)
//		for (AccessControlEntry entry : acl.getEntries()) {
//			acl.deleteAce(0);
//		}
//		acl.updateAce(0, BasePermission.CREATE);
		acl.setOwner(sid2);
		acl.insertAce(acl.getEntries().size(), BasePermission.READ, sid2, true);
//		acl.insertAce(1, BasePermission.DELETE);
//		acl.setEntriesInheriting(true);
//		acl.setParent(aclService.readAclById(new ObjectIdentityImpl(NoticeMessage.class, 6L)));
//		acl.setOwner(sid2);

		aclService.updateAcl(acl);
	}
}