package com.example.demo;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class FirebaseAuthConfig {

	@Value("classpath:javamaster-test-firebase-adminsdk-rg8lt-b49deb686a.json")
	Resource serviceAccount;

	@Bean
	FirebaseAuth firebaseAuth() throws IOException {
		if (FirebaseApp.getApps().size() > 0)
			return FirebaseAuth.getInstance(FirebaseApp.getInstance());

		var options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream())).build();

		var firebaseApp = FirebaseApp.initializeApp(options);

		return FirebaseAuth.getInstance(firebaseApp);
	}
}
