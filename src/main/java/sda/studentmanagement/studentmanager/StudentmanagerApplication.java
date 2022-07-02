package sda.studentmanagement.studentmanager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sda.studentmanagement.studentmanager.services.AttendanceService;
import sda.studentmanagement.studentmanager.services.CourseService;
import sda.studentmanagement.studentmanager.services.SessionService;
import sda.studentmanagement.studentmanager.services.UserService;


@SpringBootApplication
@EnableJpaAuditing
public class StudentmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentmanagerApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // Todo: Any other way than initialize in here?
	}

	@Bean
	CommandLineRunner run(UserService userService, CourseService courseService, SessionService sessionService, AttendanceService attendanceService) {
		return args -> {
			/*
			userService.saveRole(new Role(null, "Student"));
			userService.saveRole(new Role(null, "Professor"));
			userService.saveRole(new Role(null, "Admin"));

			userService.saveUser(new User(null, "John", "Travolta", "johnt@gmail.com", "1234", null, "Male", LocalDate.now(), "+372 5555 7777"));
			userService.saveUser(new User(null, "John", "Doe", "johndoe@gmail.com", "1234", null, "Male", LocalDate.now(), "+372 9999 2222"));
			userService.saveUser(new User(null, "Hanna", "Smith", "hanna@gmail.com", "1234", null, "Female", LocalDate.now(), "+372 8888 1111"));

			userService.addRoleToUser("johnt@gmail.com", "Student");
			userService.addRoleToUser("johndoe@gmail.com", "Professor");
			userService.addRoleToUser("hanna@gmail.com", "Admin");

			courseService.saveCourse(new Course( 1L ,"Flutter", "Mobile development", LocalDate.of(2022, 4, 18), LocalDate.of(2023, 4, 5), 4, true, null ));
			courseService.saveCourse(new Course( 2L ,"Data science", "Data manipulation and information sciences", LocalDate.of(2022, 4, 18), LocalDate.of(2023, 4, 5), 3, true, null ));
			courseService.addUserToCourse("johnt@gmail.com", "Flutter");
			courseService.addUserToCourse("johndoe@gmail.com", "Flutter");
			courseService.addUserToCourse("hanna@gmail.com", "Data science");

			sessionService.saveSession(new Session(1L, LocalDateTime.of(LocalDate.of(2022,4,20), LocalTime.of(20, 15)), 3, "Flutter basics", null, courseService.getCourse("Flutter")));

			attendanceService.saveAttendance(new Attendance(1L, true, sessionService.getSession(1L), userService.getUserById(4)));
*/
		};
	}
}
