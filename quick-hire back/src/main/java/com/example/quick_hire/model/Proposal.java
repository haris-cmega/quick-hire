// src/main/java/com/example/quick_hire/model/Proposal.java
package com.example.quick_hire.model;

import com.example.quick_hire.enums.ProposalStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "proposals")
public class Proposal {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(optional=false)
    private Job job;

    @ManyToOne(optional=false)
    private User freelancer;

    @Column(nullable=false)
    private String coverLetter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)              // and optionally length=…
    private ProposalStatus status;

    @Column(name = "bid_amount", nullable = false)
    private BigDecimal bidAmount;


    @Column(nullable=false)
    private Instant createdAt;

    // getters + setters
    public Long getId() { return id; }
    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }
    public User getFreelancer() { return freelancer; }
    public void setFreelancer(User f) { this.freelancer = f; }
    public String getCoverLetter() { return coverLetter; }
    public void setCoverLetter(String l) { this.coverLetter = l; }
    public ProposalStatus getStatus() { return status; }
    public void setStatus(ProposalStatus s) { this.status = s; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant i) { this.createdAt = i; }

    public BigDecimal getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(BigDecimal bidAmount) {
        this.bidAmount = bidAmount;
    }
}
