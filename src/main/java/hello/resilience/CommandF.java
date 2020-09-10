package hello.resilience;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.bulkhead.annotation.Bulkhead.Type;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.vavr.control.Try;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CommandF {

  public static final String COMMAND_KEY = "Command2" ;
  private static final Logger logger = LoggerFactory.getLogger(CommandF.class);

  @Retry(name = COMMAND_KEY)
  @CircuitBreaker(name = COMMAND_KEY, fallbackMethod = "fallback")
  @RateLimiter(name = COMMAND_KEY)
//  @TimeLimiter(name = COMMAND_KEY)
  @Bulkhead(name = COMMAND_KEY, type = Type.THREADPOOL)
  @Cacheable(value="newKidCache", key = "{#appToken}")
  public CompletableFuture<List<String>> execute(String appToken) {
    long start = System.currentTimeMillis();

    CompletableFuture<List<String>> future = CompletableFuture.supplyAsync(()->doSomething(appToken));
    logger.error("Get groups took " + (System.currentTimeMillis() - start));
    return future;

  }

  public CompletableFuture<List<String>> fallback(Throwable t) {
    logger.error("from fallback", t);
    return CompletableFuture.supplyAsync(Collections::emptyList);
  }

  private List<String> doSomething(String appToken){
    List<String> result = new ArrayList<>();
    int k = 10;
    while(k>=0) {
      logger.error("Command " + appToken + " iteration " + k--);
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
       logger.error("", e);
      }
    }
    return result;
  }

}
