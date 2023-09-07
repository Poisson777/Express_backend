package com.example.express_end;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import java.nio.charset.Charset;

@SpringBootApplication()
//spirngboot入口程序
public class ExpressEndApplication {
	public static void main(String[] args) {
		SpringApplication.run(ExpressEndApplication.class, args);
	}

}
