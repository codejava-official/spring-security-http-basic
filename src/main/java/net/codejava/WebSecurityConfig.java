package net.codejava;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.passwordEncoder(new BCryptPasswordEncoder())
				.withUser("namhm")
				.password("$2a$10$fCY89aAmJCp9kQ5Ejz0HveVzKSBCyGVk6YmgqSp2uzL2kqwJD/zCm")
				.roles("USER")
			.and()
				.withUser("admin")
				.password("$2a$10$K65x7/TZEpXlnDGBZSC.5u0R.iO7U1CbkZ.VIIKjSkY8uOBNmeqzK")
				.roles("ADMIN")
			;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/new").hasAnyRole("USER", "ADMIN")
			.antMatchers("/edit/*", "/delete/*").hasRole("ADMIN")
			.anyRequest().authenticated()
			.and()
			.httpBasic()
			.and()
			.exceptionHandling().accessDeniedPage("/403");
	}

	
	
}
