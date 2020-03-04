package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class NewKid {

    private static final Logger logger = LoggerFactory.getLogger(NewKid.class);

//    @Cacheable(cacheManager="newKidCache")
    @Cacheable(cacheNames = "newKidCache")
    public String whatsYourName(String input){
        logger.info("inside waht is your name " + input);
        return "New Kid Name " + input;
    }
}
