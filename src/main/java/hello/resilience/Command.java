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
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

public class Command {

  public static final String COMMAND_KEY = "Command2" ;
private static final Logger logger = LoggerFactory.getLogger(Command.class);
  private String name;

  public Command(String name) {
    this.name = name;
  }

  @Retry(name = COMMAND_KEY)
  @CircuitBreaker(name = COMMAND_KEY, fallbackMethod = "fallback")
  @RateLimiter(name = COMMAND_KEY)
  @TimeLimiter(name = COMMAND_KEY)
  @Bulkhead(name = COMMAND_KEY, type = Type.THREADPOOL)
  @Cacheable(key = "{#appToken}")
  public List<String> execute(String appToken) {
    long start = System.currentTimeMillis();

    final List<String> lasGroups = Try
        .of(Decorators.ofCheckedSupplier(() -> doSomething(appToken)).decorate())
        .get();
    logger.error("Get groups took " + (System.currentTimeMillis() - start));
    return lasGroups;


  }

  private List<String> doSomething(String appToken){
    List<String> result = new ArrayList<>();
    int k = 10;
    while(k>=0) {
      logger.error("Command " + name + " iteration " + k--);
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
       logger.error("", e);
      }
    }
    return result;
  }

}
