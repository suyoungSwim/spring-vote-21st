package com.ceos.spring_vote_21st.member.web;

import com.ceos.spring_vote_21st.member.web.dto.MemberResponseDTO;
import org.springframework.web.bind.annotation.RestController;


import com.ceos.spring_vote_21st.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    /**
     * read
     * */
    @GetMapping("/{memberId}")
    public MemberResponseDTO findOne(@PathVariable Long memberId) {
        return memberService.getMember(memberId);
    }

    @GetMapping
    public List<MemberResponseDTO> findAll() {
        return memberService.getAllMembers();
    }

    /**
     * update
     * */
/*
    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> update(@PathVariable Long id, @RequestBody MemberRequestDTO request) {
        return ResponseEntity.ok(memberService.updateMember(id, request));
    }
*/

    /**
     * delete
     * */
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> delete(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }

    /**
     * other business
     */

}
