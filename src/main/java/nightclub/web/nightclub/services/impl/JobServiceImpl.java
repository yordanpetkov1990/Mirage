package nightclub.web.nightclub.services.impl;

import nightclub.web.nightclub.entities.Job;
import nightclub.web.nightclub.repository.JobRepository;
import nightclub.web.nightclub.services.JobService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<Job> getAllJobs() {
        return this.jobRepository.findAll();
    }

    @Override
    public void addJob(Job job) {
        this.jobRepository.save(job);
    }

    @Override
    public boolean isEmpty() {
        return this.jobRepository.count() == 0;
    }

    @Override
    public Optional<Job> findById(Long id) {
        return this.jobRepository.findById(id);
    }
}
