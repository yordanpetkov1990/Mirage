package nightclub.web.nightclub.services;

import nightclub.web.nightclub.entities.Job;

import java.util.List;
import java.util.Optional;

public interface JobService {

    List<Job> getAllJobs();

    void addJob(Job job);

    boolean isEmpty();

    Optional<Job> findById(Long id);
}
