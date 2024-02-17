package myshoes.ecom;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class EcomApplicationTests {

	@Test
	void contextLoads() {
		// Test if the application context loads successfully
		ApplicationContext context = SpringApplication.run(EcomApplication.class);
		assertNotNull(context);
	}
}
