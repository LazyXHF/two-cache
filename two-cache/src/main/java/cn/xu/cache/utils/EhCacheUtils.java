package cn.xu.cache.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

@Component
public class EhCacheUtils {

    // @Autowired
    // private CacheManager cacheManager;
    @Autowired
    private EhCacheCacheManager ehCacheCacheManager;

    // 添加本地缓存 (相同的key 会直接覆盖)
    public void put(String cacheName, String key, Object value) {
        Cache cache = ehCacheCacheManager.getCacheManager().getCache(cacheName);
        Element element = new Element(key, value);
        cache.put(element);
    }

    // 获取本地缓存
    public Object get(String cacheName, String key) {
        Cache cache = ehCacheCacheManager.getCacheManager().getCache(cacheName);
        Element element = cache.get(key);
        return element == null ? null : element.getObjectValue();
    }

    public void remove(String cacheName, String key) {
        Cache cache = ehCacheCacheManager.getCacheManager().getCache(cacheName);
        cache.remove(key);
    }

}
