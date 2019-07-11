package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import static hello.CacheManagerConfig.cacheName;

@Component
public class AppRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private final BookRepository bookRepository;

    public AppRunner(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    private NewKid kid;

@Autowired
@Qualifier("new.kid")
private CacheManager newKidCacheManager;

    @Override
    public void run(String... args) throws Exception {
        String s = kid.whatsYourName("kid");
        logger.info(s);

        newKidCacheManager.getCacheNames();
        logger.info(newKidCacheManager.getCacheNames().toString());
        Cache.ValueWrapper valueWrapper = newKidCacheManager.getCache(cacheName).get("kid");
        if(valueWrapper != null) {
            logger.info(valueWrapper.toString());
            logger.info(valueWrapper.get().toString());
        } else {
            logger.info("No entry for kid");
        }

//        logger.info(newKidCacheManager.getCache(cacheName).get("kid"));

        logger.info(".... Fetching books");
        logger.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
        logger.info("isbn-4567 -->" + bookRepository.getByIsbn("isbn-4567"));
        logger.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
        logger.info("isbn-4567 -->" + bookRepository.getByIsbn("isbn-4567"));
        logger.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
        logger.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
    }

}