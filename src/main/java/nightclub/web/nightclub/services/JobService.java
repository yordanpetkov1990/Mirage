package nightclub.web.nightclub.services;

import nightclub.web.nightclub.entities.Job;
import nightclub.web.nightclub.entities.dtos.AddJobDTO;

import java.util.List;
import java.util.Optional;

public interface JobService {

    List<Job> getAllJobs();

    void addJob(AddJobDTO job);

    boolean isEmpty();

    Optional<Job> findById(Long id);
}
