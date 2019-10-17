package cn.xu.cache;

import io.lettuce.core.dynamic.support.ParameterNameDiscoverer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.DefaultParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableCaching
@MapperScan(basePackages = "cn.xu.cache.mapper")
public class CacheApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(CacheApplication.class, args);
    }





}
