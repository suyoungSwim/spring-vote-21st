package com.ceos.spring_vote_21st.vote.service;

import com.ceos.spring_vote_21st.global.error.CustomException;
import com.ceos.spring_vote_21st.global.error.ErrorCode;
import com.ceos.spring_vote_21st.member.domain.CeosPosition;
import com.ceos.spring_vote_21st.member.domain.Member;
import com.ceos.spring_vote_21st.member.repository.MemberRepository;
import com.ceos.spring_vote_21st.vote.domain.Candidate;
import com.ceos.spring_vote_21st.vote.domain.Election;
import com.ceos.spring_vote_21st.vote.domain.Section;
import com.ceos.spring_vote_21st.vote.domain.Vote;
import com.ceos.spring_vote_21st.vote.repository.ElectionRepository;
import com.ceos.spring_vote_21st.vote.repository.VoteRepository;
import com.ceos.spring_vote_21st.vote.web.dto.VoteCreateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class VoteService {
    private final ElectionRepository electionRepository;
    private final MemberRepository memberRepository;
    private final VoteRepository voteRepository;

    @Transactional
    public Long voteToCandidate(VoteCreateRequestDTO dto) {
        //find
        Election findElection = electionRepository.findById(dto.getElectionId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_EXISTS));
        Candidate findCandidate = findElection.getCandidates()
                .stream()
                .filter(candidate -> candidate.getId().equals(dto.getCandidateId()))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_EXISTS));
        Member findMember = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_EXISTS));


        //create
        //1인 한번 투표
        if (voteRepository.existsByMemberAndElection(findMember, findElection))
            throw new CustomException(ErrorCode.DUPLICATE_VOTE);


        CeosPosition position = findMember.getPosition();
        if (findElection.getSection().equals(Section.FRONT_KING) && !position.equals(CeosPosition.FRONTEND)) {
            //SRS: 본인 파트에만 투표 가능
            throw new CustomException(ErrorCode.POSITION_NOT_MATCH);

        } else if (findElection.getSection().equals(Section.BACK_KING) && !position.equals(CeosPosition.BACKEND)) {
            throw new CustomException(ErrorCode.POSITION_NOT_MATCH);
            //SRS: 본인 파트에만 투표 가능
        } else if (findElection.getSection().equals(Section.DEMO_DAY)) {
            //SRS: 본인 팀은 투표 불가능
            if (findCandidate.getTeam().equals(findMember.getTeam()))
                throw new CustomException(ErrorCode.CANNOT_VOTE_SAME_TEAM);
        }

        Vote vote = Vote.create(findMember, findCandidate, findElection);

        return voteRepository.save(vote).getId();
    }
}
