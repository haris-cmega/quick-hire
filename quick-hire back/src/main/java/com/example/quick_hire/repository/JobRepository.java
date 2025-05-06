package com.example.quick_hire.repository;

import com.example.quick_hire.enums.JobStatus;
import com.example.quick_hire.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    // find all jobs posted by a single client
    List<Job> findByClientId(Long clientId);

    // find by status
    List<Job> findByStatus(JobStatus status);
}
