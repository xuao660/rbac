package org.xa.rbac.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;


@Configuration
public class AccessTokenConfig {
	@Autowired
	public RedisConnectionFactory redisConnectionFactory;
	@Bean
	TokenStore tokenStore() {
//		使用redis存储token
		RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
//		设置redis token存储中的前缀
//		redisTokenStore.setPrefix("auth-token:");
		return redisTokenStore;
//		return new InMemoryTokenStore();
	}
}
