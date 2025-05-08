package backend_socket.backend_socket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BackendSocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendSocketApplication.class, args);
		System.out.println("Building....");
	}

}
