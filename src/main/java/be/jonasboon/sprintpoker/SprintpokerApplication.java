package be.jonasboon.sprintpoker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SprintpokerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SprintpokerApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer configureCors() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOriginPatterns("*");
			}
		};
	}

}
