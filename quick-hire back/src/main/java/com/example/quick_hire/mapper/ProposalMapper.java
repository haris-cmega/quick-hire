// src/main/java/com/example/quick_hire/mapper/ProposalMapper.java
package com.example.quick_hire.mapper;

import com.example.quick_hire.dto.ProposalResponseDTO;
import com.example.quick_hire.model.Proposal;
import java.time.LocalDateTime;
import java.time.ZoneId;


public class ProposalMapper {
    public static ProposalResponseDTO toResponseDTO(Proposal p) {
        ProposalResponseDTO out = new ProposalResponseDTO();
        out.setId(p.getId());
        out.setJobId(p.getJob().getId());
        out.setFreelancerId(p.getFreelancer().getId());
        out.setFreelancerUsername(p.getFreelancer().getUsername());
        out.setCoverLetter(p.getCoverLetter());
        out.setStatus(p.getStatus());
        out.setCreatedAt(
        LocalDateTime.ofInstant(p.getCreatedAt(), ZoneId.systemDefault()));
        out.setBidAmount(p.getBidAmount());
        return out;
    }
}
