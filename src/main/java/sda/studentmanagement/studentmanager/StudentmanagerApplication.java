package sda.studentmanagement.studentmanager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sda.studentmanagement.studentmanager.domain.Role;
import sda.studentmanagement.studentmanager.domain.User;
import sda.studentmanagement.studentmanager.services.UserService;

import java.time.LocalDate;

@SpringBootApplication
public class StudentmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentmanagerApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // Todo: Any other way than initialize in here?
	}

//	@Bean
//	CommandLineRunner run(UserService userService) {
//		return args -> {
//			userService.saveRole(new Role(null, "Student"));
//			userService.saveRole(new Role(null, "Professor"));
//			userService.saveRole(new Role(null, "Admin"));
//
//			userService.saveUser(new User(null, "John", "Travolta", "johnt@gmail.com", "1234", null, "Male", LocalDate.now(), "+372 5555 7777"));
//			userService.saveUser(new User(null, "John", "Doe", "johndoe@gmail.com", "1234", null, "Male", LocalDate.now(), "+372 9999 2222"));
//			userService.saveUser(new User(null, "Hanna", "Smith", "hanna@gmail.com", "1234", null, "Female", LocalDate.now(), "+372 8888 1111"));
//
//			userService.addRoleToUser("johnt@gmail.com", "Student");
//			userService.addRoleToUser("johndoe@gmail.com", "Professor");
//			userService.addRoleToUser("hanna@gmail.com", "Admin");
//		};
//	}
}
