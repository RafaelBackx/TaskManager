package be.ucll.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@SpringBootApplication
public class DemoApplication {

	@Bean
	public PasswordEncoder passwordEncoder(){
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler(){return new AuthenticationHandler();}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
