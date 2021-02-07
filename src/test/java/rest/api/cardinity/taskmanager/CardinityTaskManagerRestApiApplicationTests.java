package rest.api.cardinity.taskmanager;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rest.api.cardinity.taskmanager.service.DummyService;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CardinityTaskManagerRestApiApplicationTests {

	@Autowired
	private DummyService dummyService;

	@Test
	void contextLoads() {
	}

	@Test
	void seedDummyUsersTest(){
		dummyService.createDummies();
	}



}
