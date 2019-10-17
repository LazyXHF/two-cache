package cn.xu.cache.service;

import cn.xu.cache.aop.CacheInterface;
import cn.xu.cache.entity.User;
import cn.xu.cache.mapper.UserMapper;
import cn.xu.cache.utils.BaseRedisService;
import cn.xu.cache.utils.EhCacheUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * @author ~许小桀
 * @date 2019/10/17 15:40
 */
@Service
public class UserService implements Serializable {
    @Autowired
    private EhCacheUtils ehCacheUtils;
    @Autowired
    private BaseRedisService baseRedisService;

    @Autowired
    private UserMapper userMapper;

    private static final String CACHENAME_USERCACHE = "userCache";
    @CacheInterface(exprie = 1000,getCacheName = "userCache",key = "cn.xu.cache.service.UserService-getUserById-id:")
    public User getUserById(String id){
        String key = this.getClass().getName() + "-" + Thread.currentThread().getStackTrace()[1].getMethodName()
                + "-id:" + id;

        // 3. 如果二级缓存redis中也没有数据,查询数据库
        User user = userMapper.getUserById(id);
        if (user == null) {
            return null;
        }
        // 更新一级缓存和二级缓存
        String userJson = JSONObject.toJSONString(user);
        baseRedisService.setString(key, userJson, (long) 10000);
        ehCacheUtils.put(CACHENAME_USERCACHE, key, user);
        System.out.println("使用key:" + key + "，一级缓存和二级都没有数据,直接查询db" + userJson);
        return user;
    }


    }


