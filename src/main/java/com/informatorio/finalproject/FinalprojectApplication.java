package com.informatorio.finalproject;

import com.informatorio.finalproject.security.JWTAuthorizationFilter;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Arrays;

@EnableScheduling
@SpringBootApplication
public class FinalprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalprojectApplication.class, args);
	}
	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
					.exceptionHandling()
					.authenticationEntryPoint((request, response, e) ->
					{
						response.setContentType("application/json;charset=UTF-8");
						response.setStatus(HttpServletResponse.SC_FORBIDDEN);
						try {
							response.getWriter().write(new JSONObject()
									.put("timestamp", LocalDateTime.now())
									.put("details", "Access denied")
									.toString());
						} catch (JSONException ex) {
							ex.printStackTrace();
						}
					});
			http.cors()
					.and().headers().addHeaderWriter(
							new StaticHeadersWriter("Access-Control-Allow-Origin", "*"))
					.and().csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/api/v1/users/login").permitAll()
					.antMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
					.anyRequest().authenticated();
		}
	}
	@Configuration
	public class ApiBasePathConfiguration implements WebMvcConfigurer {
		private Logger logger = LoggerFactory.getLogger(getClass());

		@Override
		public void configurePathMatch(PathMatchConfigurer configurer) {
			configurer.addPathPrefix("/api/v1/", (clazz)->true);
		}
	}
/**
	@Configuration
	public class WebConfig implements WebMvcConfigurer {

		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/*").allowedOrigins("*").allowedMethods("GET", "POST", "OPTIONS", "PUT", "DELETE")
					.allowedHeaders("Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method",
							"Access-Control-Request-Headers")
					.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Access-Control-Allow-Methods")
					.allowCredentials(true).maxAge(3600);
		}
	}
	**/

}
