package com.renu.s_vs_t.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager AuthenticationManagerBean() throws Exception {

		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/resources/**")

				.permitAll();
		http.authorizeRequests().antMatchers("/webjars/**", "/css/**", "/js/**", "/images/**").permitAll();

		http.authorizeRequests()
				.antMatchers("/","/showsignup","/signup/**","/loginSuccess","/loginError","/about/**"
				,"/addme/**","/lookteachers/**","/manage/**","/addInstitutionType/**","/showManageInstitutionType/**",
				"/addInstitutionType/**","/addInstitution/**","/addJobType/**","/show-email-word/**"
				,"/send-email-word/**","/reset-password/**")
				.permitAll();
		http.authorizeRequests().antMatchers().hasAuthority("ADMIN");
		http.authorizeRequests().antMatchers().hasAuthority("USER");
		http.authorizeRequests().antMatchers("/showAddme/**").hasAnyAuthority("ADMIN","USER")
		.anyRequest().authenticated().and()
				.formLogin().loginPage("/login").successHandler(new CustomAuthenticationSuccess())
				.failureHandler(new CustomAuthenticationFailure()).permitAll().and().logout().permitAll().and()
				.exceptionHandling().accessDeniedPage("/403").and().csrf().disable();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
}
