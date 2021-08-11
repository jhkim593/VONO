package my.vono.web.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	public void configure(WebSecurity web) {
		web
		.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http
			.authorizeRequests()
				.antMatchers("/user/**").authenticated()
				.anyRequest().permitAll()
				.and()
			.formLogin()
				.loginPage("/home")
				// form action /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줌
				.loginProcessingUrl("/login")
				// 로그인 성공시 돌아가는 url
				.defaultSuccessUrl("/")
				.permitAll()
				.and()
			.logout()
				.permitAll();
	}
	
	// 패스워드 암호화
	// 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
}
