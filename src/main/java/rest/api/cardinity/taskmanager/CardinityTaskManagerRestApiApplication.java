package rest.api.cardinity.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)

public class CardinityTaskManagerRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardinityTaskManagerRestApiApplication.class, args);
	}

}
