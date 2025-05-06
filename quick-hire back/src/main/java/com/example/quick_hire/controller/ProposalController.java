package com.example.quick_hire.controller;

import com.example.quick_hire.dto.ProposalDTO;
import com.example.quick_hire.dto.ProposalResponseDTO;
import com.example.quick_hire.mapper.ProposalMapper;
import com.example.quick_hire.model.Job;
import com.example.quick_hire.model.Proposal;
import com.example.quick_hire.model.User;
import com.example.quick_hire.service.JobService;
import com.example.quick_hire.service.ProposalService;
import com.example.quick_hire.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/proposals")
public class ProposalController {
    private final ProposalService proposalService;
    private final UserService userService;
    private final JobService jobService;

    public ProposalController(ProposalService ps, UserService us, JobService js) {
        this.proposalService = ps;
        this.userService     = us;
        this.jobService      = js;
    }

    @PostMapping
    public ResponseEntity<ProposalResponseDTO> createProposal(
            @RequestBody @Valid ProposalDTO dto,
            Principal principal
    ) {
        User freelancer = userService.findByUsername(principal.getName())
                .orElseThrow();
        Job job = jobService.getJobById(dto.getJobId());
        Proposal created = proposalService.createProposal(dto, job, freelancer);
        return ResponseEntity.ok(ProposalMapper.toResponseDTO(created));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ProposalResponseDTO>> getProposalsForJob(
            @PathVariable Long jobId
    ) {
        List<Proposal> list = proposalService.getByJob(jobId);
        List<ProposalResponseDTO> dtos = list.stream()
                .map(ProposalMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProposalResponseDTO> getById(@PathVariable Long id) {
        Proposal p = proposalService.getById(id);
        return ResponseEntity.ok(ProposalMapper.toResponseDTO(p));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProposalResponseDTO> updateProposal(
            @PathVariable Long id,
            @RequestBody @Valid ProposalDTO dto,
            Principal principal
    ) {
        User freelancer = userService.findByUsername(principal.getName())
                .orElseThrow();
        Proposal updated = proposalService.updateProposal(id, dto, freelancer);
        return ResponseEntity.ok(ProposalMapper.toResponseDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProposal(
            @PathVariable Long id,
            Principal principal
    ) {
        User freelancer = userService.findByUsername(principal.getName())
                .orElseThrow();
        proposalService.deleteProposal(id, freelancer);
        return ResponseEntity.noContent().build();
    }
}
