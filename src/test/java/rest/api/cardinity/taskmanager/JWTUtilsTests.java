package rest.api.cardinity.taskmanager;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import rest.api.cardinity.taskmanager.common.utils.JWTUtils;
import rest.api.cardinity.taskmanager.service.DummyService;

import java.util.ArrayList;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JWTUtilsTests {

	@Test
	void contextLoads() {
	}

	@Test
	void generateTokenTest(){
		User user = new User("foo", "foo", new ArrayList<>());
		String token = JWTUtils.generateToken(user);
		System.out.println(token);
		Assertions.assertNotNull(token);
	}

	@Test
	void extractClaimsTest(){
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmb28iLCJleHAiOjE2MTI3MDQ3MTYsImlhdCI6MTYxMjcwNDExNn0.46tjGM7IqfJIOFQVSxWnrB28B-isrlGmdcP2yMQF1-k";
		String userName = JWTUtils.extractUserName(token);
		System.out.println(userName);
		Assertions.assertNotNull(userName);
	}



}
