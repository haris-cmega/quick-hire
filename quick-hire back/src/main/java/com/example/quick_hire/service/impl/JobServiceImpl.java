package com.example.quick_hire.service.impl;

import com.example.quick_hire.dto.JobDTO;
import com.example.quick_hire.enums.JobStatus;
import com.example.quick_hire.model.Job;
import com.example.quick_hire.model.User;
import com.example.quick_hire.repository.JobRepository;
import com.example.quick_hire.repository.UserRepository;
import com.example.quick_hire.service.JobService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public JobServiceImpl(JobRepository jobRepository, UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Job createJob(JobDTO jobDTO) {
        Optional<User> client = userRepository.findById(jobDTO.getClientId());
        if (client.isEmpty()) {
            throw new RuntimeException("Client not found");
        }

        Job job = new Job();
        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setBudget(jobDTO.getBudget());
        job.setStatus(jobDTO.getStatus() != null ? jobDTO.getStatus() : JobStatus.OPEN);
        job.setClient(client.get());

        return jobRepository.save(job);
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));
    }

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    @Override
    public Job updateJob(Long id, JobDTO jobDTO) {
        Job job = getJobById(id);
        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setBudget(jobDTO.getBudget());
        job.setStatus(jobDTO.getStatus());

        return jobRepository.save(job);
    }

    @Override
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }
}

