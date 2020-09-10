package hello.resilience;

import io.github.resilience4j.decorators.Decorators;
import io.vavr.CheckedFunction0;
import io.vavr.CheckedFunction1;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("res")
@Component
public class ResilienceRunner implements CommandLineRunner {

  @Autowired
  private CommandF commandF;
  @Autowired
  private Command command;
  @Autowired
  private CommandCached commandCached;

  @Override
  public void run(String... args) throws Exception {
    StringBuilder b = new StringBuilder();
    b.append("ebem ").append("lyobopihni");
    String temp = b.toString();
    System.out.println(temp);
    b.append(" i maimuni");

    System.out.println(temp);
    Future<ListWrapper> execute = commandCached.execute("test");
    System.out.println(" result: " + execute.get());
    execute = commandCached.execute("test");
    System.out.println(" result: " + execute.get());

//    commandCachedInCompletableFuture();
//    commandWrappedInCompletableFuture();
//    commandWithCompletableFuture();
    return;

  }

  private String returnme() {
    return new String("you called returnme");
  }

  private void commandWrappedInCompletableFuture()
      throws InterruptedException, java.util.concurrent.ExecutionException {
    List<Future<List<String>>> futureList = new ArrayList<>();
    int i = 50;
    while (i >= 0) {
      final int k = i--;
      Future<List<String>> execute = CompletableFuture.supplyAsync(() -> command.execute(k + ""));
      Future<List<String>> copy = CompletableFuture.supplyAsync(() -> command.execute(k + "`"));
      futureList.add(execute);
      futureList.add(copy);
    }
    final CompletableFuture<Void> voidCompletableFuture = CompletableFuture
        .allOf(futureList.toArray(new CompletableFuture[] {}));
    System.err.println("Start getting");
    voidCompletableFuture.get();
  }

  private void commandCachedInCompletableFuture()
      throws InterruptedException, java.util.concurrent.ExecutionException {
    List<Future<ListWrapper>> futureList = new ArrayList<>();
    int i = 5;
    while (i >= 0) {
      final int k = i--;
      Future<ListWrapper> execute = commandCached.execute(k + "");
      Future<ListWrapper> copy = commandCached.execute(k + "");
      futureList.add(execute);
      futureList.add(copy);
    }
    final CompletableFuture<Void> voidCompletableFuture = CompletableFuture
        .allOf(futureList.toArray(new CompletableFuture[] {}));
    System.err.println("Start getting");
    voidCompletableFuture.get();
  }

  private void commandWithCompletableFuture()
      throws InterruptedException, java.util.concurrent.ExecutionException {
    List<Future<List<String>>> futureList = new ArrayList<>();
    int i = 50;
    while (i >= 0) {
      i--;
      Future<List<String>> execute = commandF.execute(i + "");
      Future<List<String>> copy = commandF.execute(i + "");
      futureList.add(execute);
      futureList.add(copy);
    }
    final CompletableFuture<Void> voidCompletableFuture = CompletableFuture
        .allOf(futureList.toArray(new CompletableFuture[] {}));

    voidCompletableFuture.get();
  }

}

