package com.informatorio.finalproject.service;

import com.informatorio.finalproject.dto.RankingResponse;
import com.informatorio.finalproject.dto.VoteResponse;
import com.informatorio.finalproject.entity.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VoteServicie {
    Iterable<Vote> findAll();
    Page<Vote> findAll(Pageable pageable);
    Optional<Vote> findById(Long id);
    Vote save(Vote vote);
    void deleteById(Long id);
    Optional<Vote> findVote(Long user_id, Long emp_id);
    List<VoteResponse> findAllVotes();
    List<RankingResponse> getRankingVotesOnEvent(Long event_id);
}
