package hello.resilience;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("res")
@Component
public class ResilienceRunner implements CommandLineRunner {

  @Autowired
  private CommandF commandF;

  @Override
  public void run(String... args) throws Exception {
    final Future<List<String>> execute = commandF.execute("1");
    final Future<List<String>> execute2 = commandF.execute("2");
    execute.get();
    execute2.get();

  }

}
