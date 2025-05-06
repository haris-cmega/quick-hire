// src/main/java/com/example/quick_hire/dto/ProposalDTO.java
package com.example.quick_hire.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ProposalDTO {
    @NotNull private Long jobId;
    @NotNull private String coverLetter;

    private BigDecimal bidAmount;

    public Long getJobId() { return jobId; }
    public void setJobId(Long id) { this.jobId = id; }
    public String getCoverLetter() { return coverLetter; }
    public void setCoverLetter(String l) { this.coverLetter = l; }

    public BigDecimal getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(BigDecimal bidAmount) {
        this.bidAmount = bidAmount;
    }
}
