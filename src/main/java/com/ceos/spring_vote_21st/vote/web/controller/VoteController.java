package com.ceos.spring_vote_21st.vote.web.controller;

import com.ceos.spring_vote_21st.global.exception.CustomException;
import com.ceos.spring_vote_21st.global.response.domain.ServiceCode;
import com.ceos.spring_vote_21st.global.response.dto.CommonResponse;
import com.ceos.spring_vote_21st.security.auth.user.detail.CustomUserDetails;
import com.ceos.spring_vote_21st.vote.service.VoteService;
import com.ceos.spring_vote_21st.vote.web.dto.request.VoteCreateRequestDTO;
import com.ceos.spring_vote_21st.vote.web.dto.response.MyVoteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    // 투표 여부 및 투표한 후보
    @GetMapping("/elections/{electionId}/my-vote")
    public MyVoteDTO getMyVote(@PathVariable Long electionId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        MyVoteDTO dto = voteService.getMyVote(electionId, userDetails != null ? userDetails.getUserId() : null);

        return dto;
    }
}
