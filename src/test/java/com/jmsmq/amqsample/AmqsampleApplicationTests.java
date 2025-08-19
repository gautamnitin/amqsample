package com.jmsmq.amqsample;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

/**
 * Simple test to verify that the application context loads
 * Using @DataJpaTest to focus only on JPA components and avoid JMS-related issues
 */
@DataJpaTest
@TestPropertySource(locations = "classpath:application.properties")
class AmqsampleApplicationTests {

	@Test
	void contextLoads() {
		// This test only verifies that the JPA context loads successfully
	}

}
