package art.alex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("art.alex")
@SpringBootApplication
public class NetcreditTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetcreditTestApplication.class, args);
	}
}
