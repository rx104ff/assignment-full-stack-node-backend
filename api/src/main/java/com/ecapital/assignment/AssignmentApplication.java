package com.ecapital.assignment;

import com.ecapital.assignment.models.Employee;
import com.ecapital.assignment.repositories.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/*").allowedOrigins("http://localhost:8080");
				registry.addMapping("/*").allowedOrigins("http://localhost:3000");
			}
		};
	}

	// Load initial data if the database is empty
	@Bean
	public CommandLineRunner run(EmployeeRepository employeeRepository) throws Exception {
		return (String[] args) -> {

			if (employeeRepository.findAll().size() == 0) {
				Employee user1 = new Employee("Lewis", "Burson", 40700);
				Employee user2 = new Employee("Ian", "Malcolm", 70000);
				Employee user3 = new Employee("Ellie", "Sattler", 102000);
				Employee user4 = new Employee("Dennis", "Nedry", 52000);
				Employee user5 = new Employee("John", "Hammond", 89600);
				Employee user6 = new Employee("Ray", "Arnold", 45000);
				Employee user7 = new Employee("Laura", "Burnett", 80000);

				employeeRepository.save(user1);
				employeeRepository.save(user2);
				employeeRepository.save(user3);
				employeeRepository.save(user4);
				employeeRepository.save(user5);
				employeeRepository.save(user6);
				employeeRepository.save(user7);
			}
		};
	}
}
