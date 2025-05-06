// src/main/java/com/example/quick_hire/service/JobService.java
package com.example.quick_hire.service;

import com.example.quick_hire.dto.JobDTO;
import com.example.quick_hire.model.Job;

import java.util.List;

public interface JobService {
    Job createJob(JobDTO dto, Long clientId);
    Job getJobById(Long id);
    List<Job> getAllJobs();
    Job updateJob(Long id, JobDTO dto, Long currentUserId);
    void deleteJob(Long id, Long currentUserId);
}
