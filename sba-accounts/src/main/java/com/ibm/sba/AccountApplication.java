package com.ibm.sba;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.ibm.sba.mapper")
public class AccountApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(AccountApplication.class, args);
    }
}
