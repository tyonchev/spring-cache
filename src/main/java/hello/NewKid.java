package hello;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class NewKid {



    @Cacheable(cacheManager="new.kid")
    public String whatsYourName(String input){
        return "New Kid Name " + input;
    }
}
