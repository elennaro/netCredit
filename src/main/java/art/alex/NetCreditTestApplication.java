package art.alex;

import art.alex.entities.User;
import art.alex.repositories.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@SpringBootApplication
public class NetCreditTestApplication {

    private static final Logger logger = LoggerFactory.getLogger(NetCreditTestApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(NetCreditTestApplication.class, args);
	}

    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner demo(UsersRepository userRepo) { //TODO: remove this
        return (args) -> {
            User alex = new User("alex");
            alex.setPassword("1234");
            alex.setConfirmPassword("1234");
            alex.setPhoneNumber("593555555");
            alex.setBirthday(Date.from(LocalDate.of(1984,10, 17).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            alex.setCurrentRemainingLiabilities(new BigDecimal(0));
            alex.setMonthlySalary(new BigDecimal(5000));
            // save a user
            userRepo.registerUser(alex);

            // fetch all users
            logger.info("Users found with findAll():");
            logger.info("-------------------------------");
            for (User fetchedUser : userRepo.findAll()) {
                logger.info(fetchedUser.toString());
            }
            logger.info("");
        };
    }
}
