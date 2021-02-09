package rest.api.cardinity.taskmanager;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rest.api.cardinity.taskmanager.common.enums.ResponseCode;
import rest.api.cardinity.taskmanager.models.entity.UserDetailEntity;
import rest.api.cardinity.taskmanager.models.response.Response;
import rest.api.cardinity.taskmanager.service.DummyService;
import rest.api.cardinity.taskmanager.service.TaskService;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CardinityTaskManagerRestApiApplicationTests {

	@Autowired
	private DummyService dummyService;
	@Autowired
	private TaskService taskService;

	@Test
	void contextLoads() {
	}

	@Test
	void checkUserDetailRespTest(){
		String username = "dummy_user";
		Response<UserDetailEntity> response = taskService.getAssignmentUserDetailResponse(username);
		Assertions.assertTrue(ResponseCode.isSuccessful(response));

		UserDetailEntity entity = response.getItems();
		Assertions.assertEquals(username, entity.getUserName());
	}

/*	@Test
	void seedDummyUsersTest(){
		dummyService.createDummies();
	}*/



}
