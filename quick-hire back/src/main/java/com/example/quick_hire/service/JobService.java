package com.example.quick_hire.service;

import com.example.quick_hire.dto.JobDTO;
import com.example.quick_hire.model.Job;

import java.util.List;

public interface JobService {
    Job createJob(JobDTO jobDTO);
    Job getJobById(Long id);
    List<Job> getAllJobs();
    Job updateJob(Long id, JobDTO jobDTO);
    void deleteJob(Long id);
}
