package nightclub.web.nightclub.services.impl;

import nightclub.web.nightclub.entities.Job;
import nightclub.web.nightclub.entities.dtos.AddJobDTO;
import nightclub.web.nightclub.repository.JobRepository;
import nightclub.web.nightclub.services.JobAddedEvent;
import nightclub.web.nightclub.services.JobService;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final ApplicationEventPublisher eventPublisher;

    private final ModelMapper modelMapper;

    public JobServiceImpl(JobRepository jobRepository, ApplicationEventPublisher eventPublisher, ModelMapper modelMapper) {
        this.jobRepository = jobRepository;
        this.eventPublisher = eventPublisher;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Job> getAllJobs() {
        return this.jobRepository.findAll();
    }

    @Override
    public void addJob(AddJobDTO job) {
        Job map = this.modelMapper.map(job, Job.class);
        this.jobRepository.saveAndFlush(map);
        this.eventPublisher.publishEvent(new JobAddedEvent(this, map));
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
