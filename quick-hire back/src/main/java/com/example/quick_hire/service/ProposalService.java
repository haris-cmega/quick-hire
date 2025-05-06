package com.example.quick_hire.service;

import com.example.quick_hire.dto.ProposalDTO;
import com.example.quick_hire.model.Job;
import com.example.quick_hire.model.Proposal;
import com.example.quick_hire.model.User;

import java.util.List;

public interface ProposalService {
    Proposal createProposal(ProposalDTO dto, Job job, User freelancer);
    Proposal getById(Long id);
    List<Proposal> getByJob(Long jobId);

    // ← New:
    Proposal updateProposal(Long proposalId, ProposalDTO dto, User freelancer);

    // ← New:
    void deleteProposal(Long proposalId, User freelancer);
}
