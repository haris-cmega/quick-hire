package com.example.quick_hire.service.impl;

import com.example.quick_hire.dto.ProposalDTO;
import com.example.quick_hire.enums.ProposalStatus;
import com.example.quick_hire.model.Job;
import com.example.quick_hire.model.Proposal;
import com.example.quick_hire.model.User;
import com.example.quick_hire.repository.ProposalRepository;
import com.example.quick_hire.service.ProposalService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ProposalServiceImpl implements ProposalService {
    private final ProposalRepository repo;

    public ProposalServiceImpl(ProposalRepository repo) {
        this.repo = repo;
    }

    @Override
    public Proposal createProposal(ProposalDTO dto, Job job, User freelancer) {
        Proposal p = new Proposal();
        p.setJob(job);
        p.setFreelancer(freelancer);
        p.setCoverLetter(dto.getCoverLetter());
        p.setBidAmount(dto.getBidAmount());
        p.setStatus(ProposalStatus.SUBMITTED);
        p.setCreatedAt(Instant.now());
        return repo.save(p);
    }

    @Override
    public Proposal getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Proposal not found"));
    }

    @Override
    public List<Proposal> getByJob(Long jobId) {
        return repo.findAllByJobId(jobId);
    }

    @Override
    public Proposal updateProposal(Long proposalId, ProposalDTO dto, User freelancer) {
        Proposal existing = getById(proposalId);
        // (Optional) enforce that only the same freelancer can update:
        if (!existing.getFreelancer().getId().equals(freelancer.getId())) {
            throw new RuntimeException("Not allowed to update this proposal");
        }
        if (dto.getCoverLetter() != null) {
            existing.setCoverLetter(dto.getCoverLetter());
        }
        if (dto.getBidAmount() != null) {
            existing.setBidAmount(dto.getBidAmount());
        }
        return repo.save(existing);
    }

    @Override
    public void deleteProposal(Long proposalId, User freelancer) {
        Proposal toDelete = getById(proposalId);
        // enforce only owner can delete:
        if (!toDelete.getFreelancer().getId().equals(freelancer.getId())) {
            throw new RuntimeException("Not allowed to delete this proposal");
        }
        repo.delete(toDelete);
    }
}
