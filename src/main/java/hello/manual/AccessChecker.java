package hello.manual;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class AccessChecker {

  public static final String ACL_ATTRIBUTE = "acl_id";

  private static final Logger logger = LoggerFactory.getLogger(AccessChecker.class);

  //cache by key: token
  private static Cache<String, Map<String, List<String>>> fullAccessByTokenCache;

  @Value("${access.cache.validity.seconds:60}")
  private long cacheValidityInSeconds = 60;

  @Value("${access.cache.max.entries:50000}")
  private long cacheSize;

  public AccessChecker() {
    if (fullAccessByTokenCache == null) {
      logger.error("cacheval "+cacheValidityInSeconds);
      fullAccessByTokenCache = Caffeine.newBuilder()
          .expireAfterWrite(cacheValidityInSeconds, TimeUnit.SECONDS)
          .maximumSize(cacheSize)
          .build();
    }
  }


  public List<String> getAccess(String key, String entityId) {
    final Map<String, List<String>> ifPresent = fullAccessByTokenCache.getIfPresent(key);
    logger.error("initial get if present " + ifPresent);
    final Map<String, List<String>> fullAccessMap = fullAccessByTokenCache

        .get(key, target -> getFullAccess(key));
//    fullAccessByTokenCache.put(key, fullAccessMap);
    logger.error("get with func" + fullAccessMap);
    final ConcurrentMap<String, Map<String, List<String>>> stringMapConcurrentMap = fullAccessByTokenCache
        .asMap();
    logger.error("full cache: " + stringMapConcurrentMap);
    return fullAccessMap.getOrDefault(entityId, Arrays.asList(""));
  }

  //TODO read and populate full access from GVS and LAS
  public Map<String, List<String>> getFullAccess(String key) {
    logger.error("Enter get full access for token " + key.hashCode());
    Map<String, List<String>> accessMap = new HashMap<>();
    try {
      Thread.sleep(200);
    } catch (InterruptedException e) {
      logger.error("",e);
    }

    return accessMap;
  }


}
