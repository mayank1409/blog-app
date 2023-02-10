package com.javaguides.training.blog.app;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.javaguides.training.blog.app.entity.Role;
import com.javaguides.training.blog.app.repository.RoleRepository;

import java.util.Objects;

@SpringBootApplication
public class SpringBootBlogAppApplication implements CommandLineRunner {
	
	private final RoleRepository repository;

	public SpringBootBlogAppApplication(RoleRepository repository) {
		this.repository = repository;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBlogAppApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		if (Objects.isNull(repository.findByName("ROLE_ADMIN"))) {
			Role adminRole = new Role();
			adminRole.setName("ROLE_ADMIN");
			repository.save(adminRole);
		}
		if (Objects.isNull(repository.findByName("ROLE_USER"))) {
			Role userRole = new Role();
			userRole.setName("ROLE_USER");
			repository.save(userRole);
		}
	}
}
