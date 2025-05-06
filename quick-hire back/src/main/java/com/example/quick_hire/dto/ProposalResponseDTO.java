package com.example.quick_hire.dto;

import com.example.quick_hire.enums.ProposalStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProposalResponseDTO {
    private Long id;
    private Long jobId;
    private Long freelancerId;
    private String freelancerUsername;
    private String coverLetter;
    private ProposalStatus status;
    private LocalDateTime createdAt;

    private BigDecimal bidAmount;

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }

    public Long getFreelancerId() { return freelancerId; }
    public void setFreelancerId(Long freelancerId) { this.freelancerId = freelancerId; }

    public String getFreelancerUsername() { return freelancerUsername; }
    public void setFreelancerUsername(String freelancerUsername) { this.freelancerUsername = freelancerUsername; }

    public String getCoverLetter() { return coverLetter; }
    public void setCoverLetter(String coverLetter) { this.coverLetter = coverLetter; }

    public ProposalStatus getStatus() { return status; }
    public void setStatus(ProposalStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public BigDecimal getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(BigDecimal bidAmount) {
        this.bidAmount = bidAmount;
    }
}
