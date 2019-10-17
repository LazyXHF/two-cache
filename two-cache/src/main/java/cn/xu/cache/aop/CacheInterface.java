package cn.xu.cache.aop;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * @author ~许小桀
 * @date 2019/10/17 16:20
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheInterface {
    long exprie();
    String getCacheName();
    String key();
}
