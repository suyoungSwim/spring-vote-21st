package com.ceos.spring_vote_21st;

import com.ceos.spring_vote_21st.security.auth.application.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableConfigurationProperties(JwtProperties.class)
@SpringBootApplication
public class CeosBeSpringVote21stApplication {

	public static void main(String[] args) {
		SpringApplication.run(CeosBeSpringVote21stApplication.class, args);
	}

}
