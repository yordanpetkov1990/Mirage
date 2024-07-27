package nightclub.web.nightclub.services;

import nightclub.web.nightclub.entities.Job;
import org.springframework.context.ApplicationEvent;

public class JobAddedEvent extends ApplicationEvent {
    private final Job job;

    public JobAddedEvent(Object source, Job job) {
        super(source);
        this.job = job;
    }

    public Job getJob() {
        return job;
    }
}