package com.ceos.spring_vote_21st.vote.web.controller;

import com.ceos.spring_vote_21st.global.response.dto.CommonResponse;
import com.ceos.spring_vote_21st.vote.domain.enums.Section;
import com.ceos.spring_vote_21st.vote.service.ElectionService;
import com.ceos.spring_vote_21st.vote.web.dto.request.CandidateAddRequestDTO;
import com.ceos.spring_vote_21st.vote.web.dto.request.CandidateCreateRequestDTO;
import com.ceos.spring_vote_21st.vote.web.dto.request.CandidateModifyRequestDTO;
import com.ceos.spring_vote_21st.vote.web.dto.request.ElectionCreateRequestDTO;
import com.ceos.spring_vote_21st.vote.web.dto.response.CandidateResponseDTO;
import com.ceos.spring_vote_21st.vote.web.dto.response.CandidateWithVoteResponseDTO;
import com.ceos.spring_vote_21st.vote.web.dto.response.ElectionResultDTO;
import com.ceos.spring_vote_21st.vote.web.dto.response.ElectionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/elections")
@RequiredArgsConstructor
public class ElectionController {

    private final ElectionService electionService;



    /**
     * read
     */
    @GetMapping("/{electionId}")
    public ResponseEntity<CommonResponse<ElectionResponseDTO>> getElection(@PathVariable Long electionId) {
        ElectionResponseDTO dto = electionService.getElection(electionId);
        return ResponseEntity.ok(CommonResponse.success(dto));
    }

    // 전체 조회
    @GetMapping
    public ResponseEntity<CommonResponse<List<ElectionResponseDTO>>> getAllElections(@RequestParam(required = false) Section section) {
        List<ElectionResponseDTO> list = electionService.getAllElections(section);
        return ResponseEntity.ok(CommonResponse.success(list));
    }



    /**
     * // —— 후보(Candidate) 관련 —— //
     */



    // 선거별 후보자 조회
    @GetMapping("/{electionId}/candidates")
    public ResponseEntity<CommonResponse<List<CandidateResponseDTO>>> getAllCandidatesByElection(@PathVariable Long electionId) {
        List<CandidateResponseDTO> dtos =
                electionService.getAllCandidatesByElection(electionId);
        return ResponseEntity.ok(CommonResponse.success(dtos));
    }

    // 득표 순 정렬 조회
    @GetMapping("/{electionId}/candidates/sorts/vote-count")
    public ResponseEntity<CommonResponse<List<CandidateWithVoteResponseDTO>>> getAllCandidatesByVoteCount(
            @PathVariable Long electionId) {
        List<CandidateWithVoteResponseDTO> list =
                electionService.getAllCandidatesByElectionOrderByVoteCount(electionId);
        return ResponseEntity.ok(CommonResponse.success(list));
    }


    /**
     * other business
     */

    // 선거별 투표 결과 조회
    @GetMapping("/{electionId}/results")
    public ResponseEntity<CommonResponse<ElectionResultDTO>> getElectionResult(@PathVariable Long electionId) {
        ElectionResultDTO dto = electionService.getElectionResult(electionId);

        return ResponseEntity.ok(CommonResponse.success(dto));
    }
}

