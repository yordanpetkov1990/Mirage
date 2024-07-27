package nightclub.web.nightclub.services.impl;

import nightclub.web.nightclub.entities.FAQ;
import nightclub.web.nightclub.repository.FAQRepository;
import nightclub.web.nightclub.services.FAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final FAQService faqService;

    public DataInitializer(FAQService faqService) {
        this.faqService = faqService;
    }

    @Override
    public void run(String... args) throws Exception {
        if(faqService.isEmpty()){
            faqService.saveFAQ(new FAQ("What is the opening time?", "We open at 8 PM."));
            faqService.saveFAQ(new FAQ("Where are you located?", "We are located at 123 Nightclub Street."));
            faqService.saveFAQ(new FAQ("What is the age limit?", "Guests must be at least 18 years old."));
        }


    }
}
