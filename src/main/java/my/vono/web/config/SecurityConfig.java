package my.vono.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http
			.authorizeRequests()
			.antMatchers("/", "/home","/css/**", "/font/**", "/img/**", "/js/**", "/favicon.ico", "/scss/**", "/vendor/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/member/signUp")
				.permitAll()
				.and()
			.logout()
				.permitAll();
		
	}
}
