package nightclub.web.nightclub.services.impl;

import nightclub.web.nightclub.entities.FAQ;
import nightclub.web.nightclub.entities.Job;
import nightclub.web.nightclub.entities.dtos.AddJobDTO;
import nightclub.web.nightclub.repository.FAQRepository;
import nightclub.web.nightclub.services.FAQService;
import nightclub.web.nightclub.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final FAQService faqService;

    private final JobService jobService;

    public DataInitializer(FAQService faqService, JobService jobService) {
        this.faqService = faqService;
        this.jobService = jobService;
    }

    @Override
    public void run(String... args) throws Exception {
        if(faqService.isEmpty()){
            faqService.saveFAQ(new FAQ("What is the opening time?", "We open at 8 PM."));
            faqService.saveFAQ(new FAQ("Where are you located?", "We are located at 123 Nightclub Street."));
            faqService.saveFAQ(new FAQ("What is the age limit?", "Guests must be at least 18 years old."));
        }
        if(jobService.isEmpty()){
            jobService.addJob(new AddJobDTO("Bartender",
                    "Mix and serve drinks to customers at the bar.",
                    "Previous experience as a bartender preferred. Must have a friendly attitude and strong communication skills."));
            jobService.addJob(new AddJobDTO("DJ",
                    "Play music and manage sound systems during events.",
                    "Experience as a DJ required. Must be familiar with various music genres and sound equipment."));
            jobService.addJob(new AddJobDTO("Security Guard",
                    "Ensure the safety and security of the nightclub premises and guests.",
                    "Previous security experience preferred. Must be vigilant and able to handle difficult situations calmly."));
            jobService.addJob(new AddJobDTO("Waitstaff",
                    "Serve food and drinks to guests at their tables.",
                    "Experience in a similar role is a plus. Must have excellent customer service skills and be able to work in a fast-paced environment."));
            jobService.addJob(new AddJobDTO("Event Coordinator",
                    "Plan and oversee special events and private parties at the nightclub.",
                    "Experience in event planning required. Strong organizational and communication skills are essential."));
            jobService.addJob(new AddJobDTO("Host/Hostess",
                    "Greet guests and manage reservations.",
                    "Excellent interpersonal skills and a friendly demeanor are required. Previous experience in a similar role is a plus."));
        }


    }
}
