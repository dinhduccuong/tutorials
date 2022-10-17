package com.example.demo;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.Assert;

import com.example.demo.repo.RoleRepository;

@Configuration
public class WebSecurityConfiguration {

	@Autowired
	RoleRepository roleRepository;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());

		return http.build();
	}

	public CustomAuthenticationConverter jwtAuthenticationConverter() {
		CustomAuthenticationConverter converter = new CustomAuthenticationConverter();

		converter.setJwtGrantedAuthoritiesConverter(jwt -> {
			Set<String> roles = new HashSet<String>();

			for (String role : jwt.getClaimAsStringList("roles")) {
				List<String> privileges = roleRepository.findByRole(role).orElseThrow(NoResultException::new)
						.getPrivileges().stream().map(p -> p.getName()).collect(Collectors.toList());

				roles.add(role);
				roles.addAll(privileges);
			}

			return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		});
		return converter;
	}

	/// override custom authen convert
	static class CustomAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

		private Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

		private String principalClaimName = "id";

		@Override
		public final AbstractAuthenticationToken convert(Jwt jwt) {
			Collection<GrantedAuthority> authorities = this.jwtGrantedAuthoritiesConverter.convert(jwt);

			String principalClaimValue = jwt.getClaimAsString(this.principalClaimName);
			return new JwtAuthenticationToken(jwt, authorities, principalClaimValue);
		}

		public void setJwtGrantedAuthoritiesConverter(
				Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter) {
			Assert.notNull(jwtGrantedAuthoritiesConverter, "jwtGrantedAuthoritiesConverter cannot be null");
			this.jwtGrantedAuthoritiesConverter = jwtGrantedAuthoritiesConverter;
		}

		public void setPrincipalClaimName(String principalClaimName) {
			Assert.hasText(principalClaimName, "principalClaimName cannot be empty");
			this.principalClaimName = principalClaimName;
		}
	}
}