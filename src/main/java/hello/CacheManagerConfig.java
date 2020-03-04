package hello;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.AbstractCacheResolver;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableCaching
public class CacheManagerConfig extends CachingConfigurerSupport {

  public static String cacheName = "kjkjk";


  @Autowired
  @Qualifier(value = "newKidCache")
  CacheManager newKidCacheManager;


  @Bean
  @Override
  public CacheManager cacheManager() {
    System.out.println("build default cacheManager");
    CacheManager cmgr = new CaffeineCacheManager();
    ((CaffeineCacheManager) cmgr).setCaffeine(Caffeine.newBuilder()
        .initialCapacity(10)
        .expireAfterWrite(1000, TimeUnit.MINUTES));
    return cmgr;
  }

  @Bean
  public CacheManager newKidCache() {
    System.out.println("build newKidCacheManager");
    CacheManager cmgr = new CaffeineCacheManager("newKidCache");
    ((CaffeineCacheManager) cmgr).setCacheNames(Arrays.asList(cacheName));
    ((CaffeineCacheManager) cmgr)
        .setCaffeine(Caffeine.newBuilder()
            .initialCapacity(10)
            .expireAfterWrite(10, TimeUnit.SECONDS));
    return cmgr;
  }

//    @Bean
//    @Primary
//    @Override
//    public CacheResolver cacheResolver( ) {
//        return new CustomResolver(newKidCacheManager);
//    }
//
//
//    class CustomResolver extends AbstractCacheResolver {
//
//        private CacheManager newKidCacheManager;
//
//
//        public CustomResolver(CacheManager newKidCacheManager) {
//            this.newKidCacheManager = newKidCacheManager;
//            setCacheManager(cacheManager());
//        }
//
//        @Override
//        protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> cacheOperationInvocationContext) {
//            if (cacheOperationInvocationContext.getTarget() instanceof NewKid) {
//                return newKidCacheManager.getCacheNames();
//            } else {
//                return getCacheManager().getCacheNames();
//            }
//        }
//}
//    @Override
//    public Collection<? extends org.springframework.cache.Cache> resolveCaches(CacheOperationInvocationContext<?> cacheOperationInvocationContext) {
//      if(cacheOperationInvocationContext.getTarget() instanceof NewKid) {
//          return Arrays.asList(newKidCacheManager.getCache(cacheName));
//      } else {
//        return  cacheManager.get;
//      }
//    }
}
