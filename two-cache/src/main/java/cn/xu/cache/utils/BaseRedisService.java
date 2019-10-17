package cn.xu.cache.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class BaseRedisService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;


	public void setString(String key, Object data, Long timeout) {
		stringRedisTemplate.setEnableTransactionSupport(true);
		try {
			//开启事务
			stringRedisTemplate.multi();
			if (data instanceof String) {
				String value = (String) data;
				stringRedisTemplate.opsForValue().set(key, value);
			}
			if (timeout != null) {
				stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
			}
		}catch (Exception e){
			//事务回滚
			stringRedisTemplate.discard();
		}finally {

			stringRedisTemplate.exec();
		}

	}

	public String getString(String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}

	public void delKey(String key) {
		stringRedisTemplate.delete(key);
	}

}
