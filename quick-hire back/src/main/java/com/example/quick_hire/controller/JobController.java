// src/main/java/com/example/quick_hire/controller/JobController.java
package com.example.quick_hire.controller;

import com.example.quick_hire.dto.JobDTO;
import com.example.quick_hire.model.Job;
import com.example.quick_hire.security.CustomUserDetails;
import com.example.quick_hire.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<Job> createJob(@RequestBody @Valid JobDTO dto,
                                         Authentication auth) {
        Long clientId = ((CustomUserDetails)auth.getPrincipal()).getUser().getId();
        Job created = jobService.createJob(dto, clientId);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id,
                                         @RequestBody @Valid JobDTO dto,
                                         Authentication auth) {
        Long userId = ((CustomUserDetails)auth.getPrincipal()).getUser().getId();
        return ResponseEntity.ok(jobService.updateJob(id, dto, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id,
                                          Authentication auth) {
        Long userId = ((CustomUserDetails)auth.getPrincipal()).getUser().getId();
        jobService.deleteJob(id, userId);
        return ResponseEntity.noContent().build();
    }
}
