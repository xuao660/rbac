package org.xa.rbac.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.xa.rbac.service.MenuService;

import java.util.HashMap;
import java.util.List;
@Configuration
public  class OAuth2ServerConfig {

	
	
	
    private static final String DEMO_RESOURCE_ID = "menu";

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    	@Autowired
    	MenuService menuService;
    	
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources
            .authenticationEntryPoint(new CustomAuthExceptionEntryPoint())
            		.accessDeniedHandler(accessDeniedHandler())
            		.resourceId(DEMO_RESOURCE_ID).stateless(true);
        }
        @Bean
        public AccessDeniedHandler accessDeniedHandler() {
            return new CustomAccessDeniedHandler();
        }
        @Override
        public void configure(HttpSecurity http) throws Exception {
          
        	System.out.println("1111111111");
        	
//            http.authorizeRequests()//授权的请求
//            	.antMatchers("/role/**").hasAnyRole("admin","roleadmin")    
//            	.antMatchers("/menu/**").access("#oauth2.hasScope('select') and hasRole('admin')")          
//            	.antMatchers("/user/**").access("#oauth2.hasScope('select') and hasRole('admin')")          
        	http.authorizeRequests()
        		.antMatchers("/system/config/**").permitAll()
            	.antMatchers("/get/login/**").permitAll()
            	.antMatchers("/oauth/token/**").permitAll()
            	.and()
            	.formLogin().permitAll()
                .and().cors()
//            	.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        		.and().csrf().disable();
            
            
            List<HashMap<String,String>> maps= menuService.getAllMenusToRoles();
        	for(HashMap<String,String> map:maps) {
        		String uri=map.get("url");
        		String roles=map.get("roles");
        		System.out.println(uri+"  "+roles);
        		http.authorizeRequests().antMatchers(uri).hasAnyAuthority(roles);
        	}
        	
            	
        }
    }


    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    	@Autowired
    	TokenStore tokenStore;
    	@Autowired
    	ClientDetailsService clientDetailsService;
    	@Autowired
    	PasswordEncoder passwordEncoder;
    	@Autowired
    	private AuthenticationManager authenticationManager;

     


        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            //配置客户端,用于password认证
            clients.inMemory()
                    .withClient("client_2")
                    .resourceIds(DEMO_RESOURCE_ID)
                    .authorizedGrantTypes("password", "refresh_token")
                    .scopes("select")
                    .authorities("client")
                    .secret(passwordEncoder.encode("123"));
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
            		//.authorizationCodeServices(authorizationCodeServices())
            		.tokenServices(tokenServices())
            		.authenticationManager(authenticationManager)
            		.allowedTokenEndpointRequestMethods(HttpMethod.POST,HttpMethod.GET);
        }
        @Bean
    	AuthorizationServerTokenServices tokenServices() {
    		//设置token的刷新时间，存储位置等
    		DefaultTokenServices service=new DefaultTokenServices(); 
    		service.setClientDetailsService(clientDetailsService);
    		service.setSupportRefreshToken(true);
    		service.setTokenStore(tokenStore);
    		service.setReuseRefreshToken(true);
    		service.setAccessTokenValiditySeconds(60);
    		service.setRefreshTokenValiditySeconds(60*60*24);
    		
    		return service;
    		
    	}
//        @Bean
//    	AuthorizationCodeServices authorizationCodeServices() {
//    		//授权码存储在内存中
//    		return new InMemoryAuthorizationCodeServices();
//    	}
        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
            //允许表单认证
            oauthServer.allowFormAuthenticationForClients();
        }

    }

}
