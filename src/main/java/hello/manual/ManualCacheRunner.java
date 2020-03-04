package hello.manual;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("manual")
@Component
public class ManualCacheRunner implements CommandLineRunner {

  private static final Logger logger = LoggerFactory.getLogger(ManualCacheRunner.class);

  @Override
  public void run(String... args) throws Exception {
    AccessChecker checker = new AccessChecker();

    String key = UUID.randomUUID().toString();

    logger.error("get key first time");
    checker.getAccess(key, "test");


    logger.error("get same key again");
    checker.getAccess(key, "test");
    logger.error("get same key again");
    checker.getAccess(key, "test");
  }
}

