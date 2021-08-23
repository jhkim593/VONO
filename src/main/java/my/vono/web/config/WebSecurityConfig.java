package my.vono.web.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.RequiredArgsConstructor;
import my.vono.web.config.auth.CustomUserDetailsService;
import my.vono.web.config.oauth.CustomOauthUserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
   
	private final CustomUserDetailsService customUserDetailsService;
	private final CustomOauthUserService customOauthUserService;

	// 패스워드 암호화
   // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다
   @Bean
   public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
   @Override
   public void configure(WebSecurity web) {
      web
      .ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
   }
   
   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(customUserDetailsService);
   }
   
   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http.csrf().disable();
      http
         .authorizeRequests()
            .antMatchers("/user/**", "/newMeeting/**", "/wasteBasket/**", "/folderList/**").authenticated()
            //.anyRequest().permitAll()
            .and()
         .formLogin()
            .loginPage("/")
            // form action /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줌
            .loginProcessingUrl("/login")
            // 로그인 성공시 돌아가는 url
            .defaultSuccessUrl("/folderList")
            .failureUrl("/?error=true")// 로그인 실패 후 이동 페이지
            .usernameParameter("name")
            .passwordParameter("pw")
            .permitAll()
            .and()
          .logout()
          	.logoutSuccessUrl("/")
          	.and()
          .oauth2Login()
            .loginPage("/")
            // 구글 로그인이 완료된 뒤의 후처리
            .defaultSuccessUrl("/folderList")
            .userInfoEndpoint()
            .userService(customOauthUserService);
         
   }
   
   
   
}
