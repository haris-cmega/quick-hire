// src/main/java/com/example/quick_hire/service/impl/JobServiceImpl.java
package com.example.quick_hire.service.impl;

import com.example.quick_hire.dto.JobDTO;
import com.example.quick_hire.enums.JobStatus;
import com.example.quick_hire.enums.UserRole;
import com.example.quick_hire.exception.ResourceNotFoundException;
import com.example.quick_hire.model.Job;
import com.example.quick_hire.model.User;
import com.example.quick_hire.repository.JobRepository;
import com.example.quick_hire.repository.UserRepository;
import com.example.quick_hire.service.JobService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public JobServiceImpl(JobRepository jobRepository,
                          UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Job createJob(JobDTO dto, Long clientId) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        if (client.getRole() != UserRole.CLIENT) {
            throw new AccessDeniedException("Only clients can create jobs");
        }

        Job job = new Job();
        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setBudget(dto.getBudget());
        job.setStatus(dto.getStatus() != null ? dto.getStatus() : JobStatus.OPEN);
        job.setClient(client);

        return jobRepository.save(job);
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
    }

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    private Job ensureOwner(Long jobId, Long userId) {
        Job job = getJobById(jobId);
        if (!job.getClient().getId().equals(userId)) {
            throw new AccessDeniedException("You do not own this job");
        }
        return job;
    }

    @Override
    public Job updateJob(Long id, JobDTO dto, Long currentUserId) {
        Job job = ensureOwner(id, currentUserId);
        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setBudget(dto.getBudget());
        job.setStatus(dto.getStatus() != null ? dto.getStatus() : job.getStatus());
        return jobRepository.save(job);
    }

    @Override
    public void deleteJob(Long id, Long currentUserId) {
        Job job = ensureOwner(id, currentUserId);
        jobRepository.delete(job);
    }
}
