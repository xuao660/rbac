package org.xa.rbac.config;


import org.xa.rbac.model.RespBean;
import org.xa.rbac.model.User;
import org.xa.rbac.service.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


@Configuration
@Order(4)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
    @Autowired
    CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;
    @Autowired
    CustomUrlDecisionManager customUrlDecisionManager;
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/index.html", "/img/**", "/fonts/**", "/favicon.ico", "/verifyCode");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	// TODO Auto-generated method stub
    	System.out.println("222222222222");
    	http.cors().and()
    	.formLogin()
    	.loginPage("/login")
    	.loginProcessingUrl("/doLogin")
    	.usernameParameter("username")
    	.passwordParameter("password")
    	.permitAll()
    	.successHandler(new AuthenticationSuccessHandler() {
			
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse resp,
					Authentication authentication) throws IOException, ServletException {
				// TODO Auto-generated method stub
				  resp.setContentType("application/json;charset=utf-8");
                  PrintWriter out = resp.getWriter();
                  User user = (User)authentication.getPrincipal();
                  user.setPassword(null);
                  Map<String,Object> data=new HashMap();
                  data.put("user", user);
                  RespBean ok=RespBean.ok("登陆成功!",data);
                  String str=new ObjectMapper().writeValueAsString(ok);
                  System.out.println(str);
                  out.write(str);
                  out.flush();
                  out.close();
				
			}
		})
    	.failureHandler(new AuthenticationFailureHandler() {
			
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse resp,
					AuthenticationException exception) throws IOException, ServletException {
				// TODO Auto-generated method stub
				 resp.setContentType("application/json;charset=utf-8");
                 PrintWriter out = resp.getWriter();
                 RespBean respBean=RespBean.error("登陆失败");
                 if(exception instanceof LockedException) {
                	respBean.setMsg("账户被锁定！");
                 }else if(exception instanceof BadCredentialsException) {
                	 respBean.setMsg("用户名或密码输入错误！");
                 }
                 String str=new ObjectMapper().writeValueAsString(respBean);
                 out.write(str);
                 out.flush();
                 out.close();
			}
		}).permitAll()
    	.and()
    	.authorizeRequests()
    	//.anyRequest().fullyAuthenticated()
    	 .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
             public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                 object.setAccessDecisionManager(customUrlDecisionManager);
                 object.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource);
                 return object;
             }
         })
    	.and() 
    	
    	
    	.logout()
    	.logoutSuccessHandler(new LogoutSuccessHandler() {
			
			public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse resp, Authentication authentication)
					throws IOException, ServletException {
				// TODO Auto-generated method stub
				  resp.setContentType("application/json;charset=utf-8");
                  PrintWriter out = resp.getWriter();
                  out.write(new ObjectMapper().writeValueAsString(RespBean.ok("注销成功!")));
                  out.flush();
                  out.close();
			}
		})
    	.permitAll()
    	.and()
    	.csrf().disable().exceptionHandling()
        //没有认证时，在这里处理结果，不要重定向
        .authenticationEntryPoint((req, resp, authException) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    resp.setStatus(401);
                    PrintWriter out = resp.getWriter();
                    RespBean respBean = RespBean.error("访问失败!");
                    if (authException instanceof InsufficientAuthenticationException) {
                        respBean.setMsg("请求失败，请联系管理员!");
                    }
                    out.write(new ObjectMapper().writeValueAsString(respBean));
                    out.flush();
                    out.close();
                }
        )
        .and()
        .authorizeRequests()
        .antMatchers("/oauth/*").permitAll();
    }
    
}
