package com.shopme.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shopme.security.oauth.CustomerOAuth2UserService;
import com.shopme.security.oauth.DatabaseLoginSuccessHandler;
import com.shopme.security.oauth.OAuth2LoginSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired private CustomerOAuth2UserService oAuth2UserService;
	
	@Autowired private OAuth2LoginSuccessHandler oAuth2LoginHandler;
	
	@Autowired private DatabaseLoginSuccessHandler databaseLoginhandler;
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomerUserDetailsService();
	}
	
	@Bean
	public PasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/khachhang/**", "/giohang", "/chitiettaikhoan", "/dathang", "/checkout").authenticated()
		.anyRequest().permitAll()
		.and()
			.formLogin()
			.loginPage("/dangnhap")
			.successHandler(databaseLoginhandler)
			.usernameParameter("email").permitAll()
		.and()
			.oauth2Login()
			.loginPage("/dangnhap")
			.userInfoEndpoint()
			.userService(oAuth2UserService)
			.and()
			.successHandler(oAuth2LoginHandler)
		.and()
				.logout().permitAll();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(PasswordEncoder());

		return authProvider;
	}
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}




	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
	}
	
//	@Autowired
//	private CustomLogoutSuccessHandler logoutSuccessHandler;
	

}
