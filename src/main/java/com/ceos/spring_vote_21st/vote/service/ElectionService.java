package com.ceos.spring_vote_21st.vote.service;

import com.ceos.spring_vote_21st.global.exception.CustomException;
import com.ceos.spring_vote_21st.global.response.domain.ServiceCode;
import com.ceos.spring_vote_21st.member.repository.MemberRepository;
import com.ceos.spring_vote_21st.vote.domain.*;
import com.ceos.spring_vote_21st.vote.domain.enums.Section;
import com.ceos.spring_vote_21st.vote.repository.ElectionRepository;
import com.ceos.spring_vote_21st.vote.repository.VoteRepository;
import com.ceos.spring_vote_21st.vote.web.dto.request.CandidateAddRequestDTO;
import com.ceos.spring_vote_21st.vote.web.dto.request.CandidateCreateRequestDTO;
import com.ceos.spring_vote_21st.vote.web.dto.request.CandidateModifyRequestDTO;
import com.ceos.spring_vote_21st.vote.web.dto.request.ElectionCreateRequestDTO;
import com.ceos.spring_vote_21st.vote.web.dto.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ElectionService {
    private final MemberRepository memberRepository;

    private final ElectionRepository electionRepository;

    private final VoteRepository voteRepository;
    /**
     * 선거 생성: candidates 목록까지 함께 저장됩니다.
     */
    @Transactional
    public Long createElection(ElectionCreateRequestDTO dto) {
        Election election = Election.builder()
                .name(dto.getName())
                .electionStatus(dto.getElectionStatus())
                .section(dto.getSection())
                .startedAt(dto.getStartedAt())
                .finishedAt(dto.getFinishedAt())
                .build();

        log.info("candidatesDTO 로깅: "+ dto.getCandidates().toString());
        // cascade = ALL 이므로, Election에 붙여 두면 save() 시 함께 persist 됩니다.
        dto.getCandidates().forEach(candidateDTO -> {
            Candidate c = Candidate.create(election, candidateDTO.getName(), candidateDTO.getTeam());
            election.addCandidate(c);
        });


        return electionRepository.save(election).getId();
    }

    /**
     * 단건 조회
     */
    public ElectionResponseDTO getElection(Long id) {
        return electionRepository.findById(id)
                .map(ElectionResponseDTO::from)
                .orElseThrow(() -> new CustomException(ServiceCode.ENTITY_NOT_EXISTS));
    }

    /**
     * 전체 조회
     */
    public List<ElectionResponseDTO> getAllElections(Section section) {
        List<Election> elections = (section == null) ?
                electionRepository.findAll() :
                electionRepository.findBySection(section);

        return elections.stream()
                .map(ElectionResponseDTO::from)
                .collect(Collectors.toList());
    }


/*

    */
/**
     * 업데이트: 후보 목록을 새로 바꾸면
     * orphanRemoval = true 에 의해 삭제/추가가 자동으로 처리됩니다.
     *//*

    @Transactional
    public ElectionResponseDTO updateElection(Long id, ElectionRequestDTO dto) {
        Election election = electionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 선거가 없습니다. id=" + id));

        election.setName(dto.getName());
        election.setElectionStatus(dto.getElectionStatus());
        election.setStartedAt(dto.getStartedAt());
        election.setFinishedAt(dto.getFinishedAt());

        // 후보 목록 교체
        election.getCandidates().clear();
        dto.getCandidates().forEach(cReq -> {
            Candidate c = Candidate.builder()
                    .name(cReq.getName())
                    .election(election)
                    .build();
            election.getCandidates().add(c);
        });

        Election updated = electionRepository.save(election);
        return from(updated);
    }
*/

    /**
     * 삭제: cascade + orphanRemoval 옵션으로 Vote·Candidate 모두 함께 삭제
     */
    @Transactional
    public void deleteElection(Long id) {
        if (!electionRepository.existsById(id)) {
            throw new CustomException(ServiceCode.INVALID_TOKEN.ENTITY_NOT_EXISTS);
        }
        electionRepository.deleteById(id);
    }


    /**
     * Candidate CRUD
     */

    //create
    @Transactional
    public Long addCandidate(CandidateAddRequestDTO dto) {
        //find election
        Election findElection = electionRepository.findById(dto.getElectionId())
                .orElseThrow(() -> new CustomException(ServiceCode.MEMBER_NOT_EXISTS));

        Candidate candidate = Candidate.create(findElection, dto.getName(),dto.getTeam());

        //save
        electionRepository.save(findElection);

        return candidate.getId();
    }


    //read
    public List<CandidateResponseDTO> getAllCandidatesByElection(Long electionId) {
        Election findElection = electionRepository.findById(electionId)
                .orElseThrow(()->new CustomException(ServiceCode.ENTITY_NOT_EXISTS));

        return findElection.getCandidates().stream()
                .map(CandidateResponseDTO::from)
                .collect(Collectors.toList());
    }

    public List<CandidateWithVoteResponseDTO> getAllCandidatesByElectionOrderByVoteCount(Long electionId) {
        Election findElection = electionRepository.findById(electionId)
                .orElseThrow(()->new CustomException(ServiceCode.ENTITY_NOT_EXISTS));

        List<CandidateWithVoteResponseDTO> dtos = findElection.getCandidates().stream()
                .map(candidate ->
                {
                    // TODO: 어차피 후보자별 투표 수를 보여주는게 목표니까 VoteRepository에서도 조회 가능
                    int voteCount = voteRepository.findAllByCandidateAndElection(candidate, findElection).size();
                    return CandidateWithVoteResponseDTO.from(candidate, voteCount);
                })
                .sorted((a, b) -> -(a.getVoteCount() - b.getVoteCount()))   //내림차순
                .collect(Collectors.toList());

        return dtos;
    }

    //update
    @Transactional
    public Long modifyCandidate(CandidateModifyRequestDTO dto) {
        //find election
        Election findElection = electionRepository.findById(dto.getElectionId())
                .orElseThrow(() -> new CustomException(ServiceCode.MEMBER_NOT_EXISTS));

        List<Candidate> candidates = findElection.getCandidates();

        //find candidate
        Candidate findCandidate = getCandidateById(dto.getCandidateId(), candidates);

        findCandidate.update(dto);

        electionRepository.save(findElection);
        return findCandidate.getId();
    }

    @Transactional
    public void deleteCandidate(Long electionId, Long candidateId) {
        Election findElection = electionRepository.findById(electionId)
                .orElseThrow(() -> new CustomException(ServiceCode.ENTITY_NOT_EXISTS));

        Candidate findCandidate = findElection.getCandidates()
                .stream()
                .filter(candidate -> candidate.getId().equals(candidateId))
                .findFirst()
                .orElseThrow(() -> new CustomException(ServiceCode.ENTITY_NOT_EXISTS));

        findElection.removeCandidate(findCandidate);
    }
    @Transactional
    public void deleteAll() {
        electionRepository.deleteAll();
    }
    /**
     * other business
     * */
    private static Candidate getCandidateById(Long candidateId, List<Candidate> candidates) {
        Candidate findCandidate = candidates.stream()
                .filter(candidate -> candidate.getId().equals(candidateId))
                .findFirst()
                .orElseThrow(() -> new CustomException(ServiceCode.ENTITY_NOT_EXISTS));
        return findCandidate;
    }


    // 선겨별 투표 결과 조회
    public ElectionResultDTO getElectionResult(Long electionId) {
        Election findElection = electionRepository.findById(electionId)
                .orElseThrow(() -> new CustomException(ServiceCode.ENTITY_NOT_EXISTS));

        List<VoteCount4CandidateDTO> voteCounts = voteRepository.findVoteCountsByElection(electionId);

        return ElectionResultDTO.from(ElectionResponseDTO.from(findElection), voteCounts);
    }


}
