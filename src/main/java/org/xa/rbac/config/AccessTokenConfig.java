package org.xa.rbac.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.*;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class AccessTokenConfig {

	@Bean
	TokenStore tokenStore() {
		//TokenStore是一个接口，实现类InMemoryTokenStore、JdbcTokenStore、JwkTokenStore、RedisTokenStore。
		//作用是设置服务端保存token的方式
		// TODO Auto-generated method stub
		return new InMemoryTokenStore();
	}
}
