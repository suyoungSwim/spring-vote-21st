package com.ceos.spring_vote_21st.vote.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class VoteServiceTest {
    @BeforeAll
    static void setUp() {
        Dotenv env = Dotenv.configure()
                .filename(".env.test")
                .ignoreIfMissing()
                .load();
        env.entries().forEach(dotenvEntry -> System.setProperty(dotenvEntry.getKey(), dotenvEntry.getValue()));

    }

    @Test
    void hello() {

    }
}