package org.xa.rbac;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableCaching
@MapperScan(basePackages = "org.xa.rbac.mapper")
@EnableScheduling
public class RBACApplication {

	public static void main(String[] args) {
		
	     SpringApplication.run(RBACApplication.class, args);
	}

}

