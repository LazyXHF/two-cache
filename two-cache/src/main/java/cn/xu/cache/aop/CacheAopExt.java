package cn.xu.cache.aop;

import cn.xu.cache.entity.User;
import cn.xu.cache.utils.BaseRedisService;
import cn.xu.cache.utils.EhCacheUtils;
import com.alibaba.fastjson.JSONObject;
import com.sun.javaws.CacheUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author ~许小桀
 * @date 2019/10/17 16:28
 */
@Aspect
@Component
public class CacheAopExt {

    @Autowired
    private EhCacheUtils ehCacheUtils;
    @Autowired
    private BaseRedisService baseRedisService;

    @Pointcut("execution(public * cn.xu.cache.service.*.*(..))")
    public void getAopPoint(){

    }

    @Around("getAopPoint()")
    public Object getCache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        //使用java反射机制获取方法上使用有注解@CacheInterce
        CacheInterface cacheExt = methodSignature.getMethod().getDeclaredAnnotation(CacheInterface.class);
        if (cacheExt ==null){
            Object proceed = proceedingJoinPoint.proceed();
            return proceed;
        }
        //获取接口的参数
        long exprie = cacheExt.exprie();
        String cacheName = cacheExt.getCacheName();
        String key = cacheExt.key();

        // 方法参数值参数值
        Object[] args = proceedingJoinPoint.getArgs();
        StringBuffer sb =  new StringBuffer();
        for (int i = 1; i <=args.length ; i++) {
            if (i < args.length-1){
                sb.append(args[i]+",");
            }else {
                sb.append(args[i-1]);
            }
        }
        //真正的key值
        String trueKey =key+sb.toString();
        // 1.先查找一级缓存(本地缓存),如果本地缓存有数据直接返回
        User ehUser = (User) ehCacheUtils.get(cacheName,trueKey);
        if (ehUser != null) {
            System.out.println("使用key:" + key + ",查询一级缓存 ehCache 获取到ehUser:" + JSONObject.toJSONString(ehUser));
            return ehUser;
        }
        // 2. 如果本地缓存没有该数据，直接查询二级缓存(redis)
        String redisUserJson = baseRedisService.getString(trueKey);
        if (!StringUtils.isEmpty(redisUserJson)) {
            // 将json 转换为对象(如果二级缓存redis中有数据直接返回二级缓存)
            JSONObject jsonObject = new JSONObject();
            User user = jsonObject.parseObject(redisUserJson, User.class);
            // 更新一级缓存
            ehCacheUtils.put(cacheName, key+sb.toString(), user);
            System.out.println("使用key:" + key + ",查询二级缓存 redis 获取到ehUser:" + JSONObject.toJSONString(user));
            return user;
        }
        return proceedingJoinPoint.proceed();

    }


}
