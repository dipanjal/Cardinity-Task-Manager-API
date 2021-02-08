package rest.api.cardinity.taskmanager;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import rest.api.cardinity.taskmanager.common.utils.JWTUtils;
import rest.api.cardinity.taskmanager.service.ProjectService;

import java.util.ArrayList;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectServiceTest {

	@Autowired
	ProjectService projectService;

	@Test
	public void deleteProjectTest(){
		projectService.deleteProject(2);
	}
}
