package com.informatorio.finalproject.service;

import com.informatorio.finalproject.dto.RankingResponse;
import com.informatorio.finalproject.dto.VoteResponse;
import com.informatorio.finalproject.entity.Vote;
import com.informatorio.finalproject.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoteServiceImplementation implements VoteServicie {
    @Autowired
    private VoteRepository voteRepository;
    @Override
    public Iterable<Vote> findAll() {
        return voteRepository.findAll();
    }

    @Override
    public Page<Vote> findAll(Pageable pageable) {
        return voteRepository.findAll(pageable);
    }

    @Override
    public Optional<Vote> findById(Long id) {
        return voteRepository.findById(id);
    }

    @Override
    public Vote save(Vote vote) {
        return voteRepository.save(vote);
    }

    @Override
    public void deleteById(Long id) {
        voteRepository.deleteById(id);
    }

    @Override
    public Optional<Vote> findVote(Long user_id, Long emp_id) {
        return voteRepository.findVote(user_id, emp_id);
    }

    @Override
    public List<VoteResponse> findAllVotes() {
        return voteRepository.findAllVotes();
    }

    @Override
    public List<RankingResponse> getRankingVotesOnEvent(Long event_id) {
        return voteRepository.getRankingVotesOnEvent(event_id);
    }


}
