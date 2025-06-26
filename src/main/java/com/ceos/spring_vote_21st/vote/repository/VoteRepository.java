package com.ceos.spring_vote_21st.vote.repository;

import com.ceos.spring_vote_21st.member.domain.Member;
import com.ceos.spring_vote_21st.vote.domain.Candidate;
import com.ceos.spring_vote_21st.vote.domain.Election;
import com.ceos.spring_vote_21st.vote.domain.Vote;
import com.ceos.spring_vote_21st.vote.web.dto.response.VoteCount4CandidateDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findAllByCandidateAndElection(Candidate candidate, Election election);

    @Query(value = "select * from Vote v where v.member_id=:memberId and v.election_id=:electionId for update", nativeQuery = true)
    Optional<Vote> findVoteForUpdate(@Param("memberId") Long memberId, @Param("electionId") Long electionId);

    Optional<Vote> findByElectionIdAndMemberId(Long electionId, Long memberId);


    @Query("select new com.ceos.spring_vote_21st.vote.web.dto.response.VoteCount4CandidateDTO(c.election.id, c.name, c.id, COUNT(v)) " +
            "from Candidate c left join Vote v on c.id = v.candidate.id " +
            "where c.election.id = :electionId " +
            "group by c.id, c.name, c.election.id")
    List<VoteCount4CandidateDTO> findVoteCountsByElection(@Param("electionId") Long electionId);

}
