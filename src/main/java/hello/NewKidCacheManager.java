package hello;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

import static hello.CacheManagerConfig.cacheName;

@Configuration
public class NewKidCacheManager {

    @Bean("new.kid")
    public CacheManager newKidCacheManager() {
        System.out.println("build newKidCacheManager");
        CacheManager cmgr = new CaffeineCacheManager();
        ((CaffeineCacheManager) cmgr).setCacheNames(Arrays.asList(cacheName));
        ((CaffeineCacheManager) cmgr).setCaffeine(Caffeine.newBuilder());
        return cmgr;
    }

}
