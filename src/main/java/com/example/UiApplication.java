package com.example;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
//import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@SpringBootApplication
//@RestController
//@CrossOrigin(origins="http://localhost:4200", allowedHeaders="*")
//@RequestMapping("/")
//public class UiApplication {
//	
//	@RequestMapping("/resource")
//	@GetMapping("/resource")
//	public Map<String, Object> home(){
//		Map<String, Object> model = new HashMap<String, Object>();
//		model.put("id", UUID.randomUUID().toString());
//		model.put("content", "Hello World");
//		return model;
//	}
//
//	public static void main(String[] args) {
//		SpringApplication.run(UiApplication.class, args);
//	}
//}

@SpringBootApplication
@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping("/")
public class UiApplication extends SpringBootServletInitializer {

	@GetMapping("/resource")
	public Map<String, Object> home() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", UUID.randomUUID().toString());
		model.put("content", "Hello World");
		return model;
	}

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(UiApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(UiApplication.class, args);

	}

	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.httpBasic()
				.and().authorizeRequests().antMatchers("/index.html", "/", "/home", "/login").permitAll()
				.anyRequest().authenticated()
				.and().csrf()
		        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		}
	}

}
