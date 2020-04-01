package com.ibm.sba.routes.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ibm.sba.routes.filter.JwtTokenAuthenticationFilter;



@EnableWebSecurity 	// Enable security config. This annotation denotes config for spring security.
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private JwtConfig jwtConfig;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		final String[] SWAGGER_UI = { "/", "/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs", "/webjars/**",
				"/actuator/**", "/account/v2/api-docs", "/mentor/v2/api-docs", "/course/v2/api-docs", "/payment/v2/api-docs",
				"/account/api/v1/register", "/account/api/v1/getUsersByIds",
				"/mentor/api/v1/skills", "/mentor/api/v1/getMentorSkills", "/mentor/api/v1/getMentorsByFilter"};

		web.ignoring().antMatchers(SWAGGER_UI);
	}
 
	@Override
  	protected void configure(HttpSecurity http) throws Exception {
    	   http
    	   .cors().and()
		.csrf().disable()
		    // make sure we use stateless session; session won't be used to store user's state.
	 	    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) 	
		.and()
		    // handle an authorized attempts 
		    .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED)) 	
		.and()
		   // Add a filter to validate the tokens with every request
		   .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
		// authorization requests config
		.authorizeRequests()
		   // allow all who are accessing "auth" service
		   .antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()
		   .antMatchers(HttpMethod.POST, "/account/api/v1/add").permitAll() 
		   .antMatchers(HttpMethod.GET, "/course/api/v1/mentors/list").permitAll()
//		   .antMatchers(HttpMethod.GET, "/account/api/v1/query").permitAll()
		   // must be an admin if trying to access admin area (authentication is also required here)
		   .antMatchers("/account/**", "/course/**", "/mentor/**").hasAnyRole("admin","mentor")
//		   .antMatchers(HttpMethod.POST, "/course/**", "/account/**").hasAnyRole("admin","mentor")
		   .antMatchers(HttpMethod.POST, "/course/**").hasRole("user")
		   // Any other request must be authenticated
		   .anyRequest().authenticated(); 
	}
	
	@Bean
  	public JwtConfig jwtConfig() {
    	   return new JwtConfig();
  	}
}
