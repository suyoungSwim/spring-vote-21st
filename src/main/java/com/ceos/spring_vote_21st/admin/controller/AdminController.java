package com.ceos.spring_vote_21st.admin.controller;

import com.ceos.spring_vote_21st.global.response.dto.CommonResponse;
import com.ceos.spring_vote_21st.member.service.MemberService;
import com.ceos.spring_vote_21st.vote.service.ElectionService;
import com.ceos.spring_vote_21st.vote.web.dto.request.CandidateAddRequestDTO;
import com.ceos.spring_vote_21st.vote.web.dto.request.CandidateModifyRequestDTO;
import com.ceos.spring_vote_21st.vote.web.dto.request.ElectionCreateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@RestController
public class AdminController {
    private final MemberService memberService;
    private final ElectionService electionService;

    /**
     * Member
     * */
    @DeleteMapping("/members/{memberId}")
    public ResponseEntity<Void> delete(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();  // ResponseEntity.noContent()는 응답 바디를 아예 쓰지않음.(wrapping은 되지만 클라이언트는 바디를 안받는다)
    }

    @DeleteMapping("/members")
    public ResponseEntity<Void> deleteAll() {
        memberService.deleteAll();
        return ResponseEntity.noContent().build();
    }

    /**
     * Election
     */
    @PostMapping("/elections")
    public ResponseEntity<CommonResponse<Long>> createElection(@RequestBody ElectionCreateRequestDTO dto) {
        Long id = electionService.createElection(dto);
        return ResponseEntity.ok(CommonResponse.success(id));
    }


    @DeleteMapping("/elections/{electionId}")
    public ResponseEntity<Void> deleteElection(@PathVariable Long electionId) {
        electionService.deleteElection(electionId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/elections")
    public ResponseEntity<Void> deleteAllElections() {
        electionService.deleteAll();
        return ResponseEntity.noContent().build();
    }

    /**
     * Candidate
     */

    // 후보 추가
    @PostMapping("/elections/{electionId}/candidates")
    public ResponseEntity<CommonResponse<Long>> addCandidate(
            @RequestBody CandidateAddRequestDTO dto) {
        Long id = electionService.addCandidate(dto);
        return ResponseEntity.ok(CommonResponse.success(id));
    }

    // 후보 정보 수정
    @PutMapping("/elections/{electionId}/candidates")
    public ResponseEntity<CommonResponse<Long>> modifyCandidate(
            @RequestBody CandidateModifyRequestDTO dto) {
        Long id = electionService.modifyCandidate(dto);
        return ResponseEntity.ok(CommonResponse.success(id));
    }

    // delete
    @DeleteMapping("/elections/{electionId}/candidates/{candidateId}")
    public ResponseEntity<CommonResponse<?>> deleteCandidate(@PathVariable Long electionId, @PathVariable Long candidateId) {
        electionService.deleteCandidate(electionId, candidateId);

        return ResponseEntity.ok(CommonResponse.success(null));
    }


}
