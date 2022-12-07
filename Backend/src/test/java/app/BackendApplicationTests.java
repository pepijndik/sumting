package app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class BackendApplicationTests {


	@Autowired
	SumtingBackend application = null;

	@Test
	void SumtingBackendLoad(){
		assertNotNull(application);
		System.out.println("Application auto-configuration has succeeded.");

	}

	@Test
	public void jUnit5HasBeenConfiguredForTesting() {
		assertThrows(RuntimeException.class, () -> { int a = 0; int b = 1 / a; });
	}

}
