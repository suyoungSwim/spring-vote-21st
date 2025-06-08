package com.ceos.spring_vote_21st;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class CeosBeSpringVote21stApplicationTests {

	@BeforeAll
	static void setUp() {
		Dotenv env = Dotenv.configure()
				.filename(".env.test")
				.ignoreIfMissing()
				.load();
		env.entries().forEach(dotenvEntry -> System.setProperty(dotenvEntry.getKey(), dotenvEntry.getValue()));

	}
	@Test
	void contextLoads() {
	}

}
