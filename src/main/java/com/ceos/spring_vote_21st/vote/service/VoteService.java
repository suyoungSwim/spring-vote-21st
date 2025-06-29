package com.ceos.spring_vote_21st.vote.service;

import com.ceos.spring_vote_21st.global.exception.CustomException;
import com.ceos.spring_vote_21st.global.response.domain.ServiceCode;
import com.ceos.spring_vote_21st.member.domain.CeosPosition;
import com.ceos.spring_vote_21st.member.domain.Member;
import com.ceos.spring_vote_21st.member.repository.MemberRepository;
import com.ceos.spring_vote_21st.vote.domain.Candidate;
import com.ceos.spring_vote_21st.vote.domain.Election;
import com.ceos.spring_vote_21st.vote.domain.enums.Section;
import com.ceos.spring_vote_21st.vote.domain.Vote;
import com.ceos.spring_vote_21st.vote.repository.ElectionRepository;
import com.ceos.spring_vote_21st.vote.repository.VoteRepository;
import com.ceos.spring_vote_21st.vote.web.dto.request.VoteCreateRequestDTO;
import com.ceos.spring_vote_21st.vote.web.dto.response.CandidateResponseDTO;
import com.ceos.spring_vote_21st.vote.web.dto.response.MyVoteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class VoteService {
    private final ElectionRepository electionRepository;
    private final MemberRepository memberRepository;
    private final VoteRepository voteRepository;

    @Transactional
    public Long voteToCandidate(VoteCreateRequestDTO dto, Long voterId) {
        //find
        Election findElection = electionRepository.findById(dto.getElectionId())
                .orElseThrow(() -> new CustomException(ServiceCode.ENTITY_NOT_EXISTS));
        Candidate findCandidate = findElection.getCandidates()
                .stream()
                .filter(candidate -> candidate.getId().equals(dto.getCandidateId()))
                .findFirst()
                .orElseThrow(() -> new CustomException(ServiceCode.ENTITY_NOT_EXISTS));
        Member findMember = memberRepository.findById(voterId)
                .orElseThrow(() -> new CustomException(ServiceCode.ENTITY_NOT_EXISTS));


        //create
        //1인 한번 투표
        if (voteRepository.findVoteForUpdate(findMember.getId(), findElection.getId()).isPresent())
            throw new CustomException(ServiceCode.DUPLICATE_VOTE);


        CeosPosition position = findMember.getPosition();
        if (findElection.getSection().equals(Section.FRONT_KING) && !position.equals(CeosPosition.FRONTEND)) {
            //SRS: 본인 파트에만 투표 가능
            throw new CustomException(ServiceCode.POSITION_NOT_MATCH);

        } else if (findElection.getSection().equals(Section.BACK_KING) && !position.equals(CeosPosition.BACKEND)) {
            throw new CustomException(ServiceCode.POSITION_NOT_MATCH);
            //SRS: 본인 파트에만 투표 가능
        } else if (findElection.getSection().equals(Section.DEMO_DAY)) {
            //SRS: 본인 팀은 투표 불가능
            if (findCandidate.getTeam().equals(findMember.getTeam()))
                throw new CustomException(ServiceCode.CANNOT_VOTE_SAME_TEAM);
        }

        Vote vote = Vote.create(findMember, findCandidate, findElection);

        return voteRepository.save(vote).getId();
    }

    public MyVoteDTO getMyVote(Long electionId, Long memberId) {
        if (memberId == null) {
            return MyVoteDTO.create(false, null);
        }

        Optional<Vote> optionalVote = voteRepository.findByElectionIdAndMemberId(electionId, memberId);

        if (optionalVote.isPresent()) {
            // 투표한 경우 → true + 후보자 정보 반환
            Vote vote = optionalVote.get();
            return MyVoteDTO.create(true, CandidateResponseDTO.from(vote.getCandidate()));
        } else {
            // 투표 안 한 경우
            return MyVoteDTO.create(false, null);
        }
    }
}
