// src/main/java/com/example/quick_hire/repository/ProposalRepository.java
package com.example.quick_hire.repository;

import com.example.quick_hire.model.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProposalRepository extends JpaRepository<Proposal,Long> {
    List<Proposal> findAllByJobId(Long jobId);
}
