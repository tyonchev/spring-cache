package util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@ConfigurationProperties("timer")
@Component
public class AspectTimer {

  private static final Logger logger = LoggerFactory.getLogger(AspectTimer.class);

  private StopWatch timer = new StopWatch("timer");
//  @Pointcut("execution(* hello.*(..))")
//  public void methodToTime() {};

  @Around("execution(* *.*(..))")
  public Object profile(ProceedingJoinPoint pjp) throws Throwable {
    try {
      final String methodName = "";//pjp.getSignature().getName();
      timer.start();
      System.err.println("#### Start executing " + methodName);
      logger.error("#### Start executing " + methodName);
      Object result = pjp.proceed();
      timer.stop();
      logger.error("#### End executing " + methodName + ", took " + timer.getLastTaskTimeMillis());
      return result;
    } finally {
      System.err.println("#### finally executing ");
      logger.error("#### finally executing ");
    }
  }

}


