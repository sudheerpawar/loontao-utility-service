package com.loontao.utilityservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
// Added below annotation to avoid java.lang.IllegalArgumentException at HttpSecurity.java:3367
@AutoConfigureMockMvc(addFilters = false) // Disable filters for tests
class utilityserviceApplicationTests {

	@Test
	void contextLoads() {
	}

}
