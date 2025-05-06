package com.example.quick_hire.repository;

import com.example.quick_hire.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
