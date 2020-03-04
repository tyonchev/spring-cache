package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static hello.CacheManagerConfig.cacheName;

@Profile("cache")
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
@Qualifier("newKidCache")
private CacheManager newKidCacheManager;

    @Override
    public void run(String... args) throws Exception {
        final String kidName = "kid";
        logger.info("Call whats your name");
        String s = this.kid.whatsYourName(kidName);
        logger.info(s);

        logger.info("Call whats your name again");
        this.kid.whatsYourName(kidName);
        logger.info("Call whats your name again");
        this.kid.whatsYourName(kidName);

        newKidCacheManager.getCacheNames();
        logger.info(newKidCacheManager.getCacheNames().toString());
        Cache.ValueWrapper valueWrapper = newKidCacheManager.getCache(cacheName).get(kidName);
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