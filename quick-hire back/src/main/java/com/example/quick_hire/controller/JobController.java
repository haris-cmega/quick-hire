package com.example.quick_hire.controller;

import com.example.quick_hire.dto.JobDTO;
import com.example.quick_hire.model.Job;
import com.example.quick_hire.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody JobDTO jobDTO) {
        Job createdJob = jobService.createJob(jobDTO);
        return ResponseEntity.ok(createdJob);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        Job job = jobService.getJobById(id);
        return ResponseEntity.ok(job);
    }

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody JobDTO jobDTO) {
        Job updatedJob = jobService.updateJob(id, jobDTO);
        return ResponseEntity.ok(updatedJob);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }
}
