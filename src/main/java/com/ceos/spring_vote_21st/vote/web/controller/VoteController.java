package com.ceos.spring_vote_21st.vote.web.controller;

import com.ceos.spring_vote_21st.global.response.dto.CommonResponse;
import com.ceos.spring_vote_21st.vote.service.VoteService;
import com.ceos.spring_vote_21st.vote.web.dto.request.VoteCreateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    /**
     * 후보에 투표합니다.
     * @param dto electionId, candidateId, memberId 가 포함된 요청 DTO
     * @return 생성된 Vote 엔티티 ID
     */
    @PostMapping("/elections/{electionId}/votes")
    public ResponseEntity<CommonResponse<Long>> voteToCandidate(
            @RequestBody VoteCreateRequestDTO dto) {
        Long voteId = voteService.voteToCandidate(dto);
        return ResponseEntity.ok(CommonResponse.success(voteId));
    }
}
