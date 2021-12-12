package com.informatorio.finalproject.dto;

import java.util.List;

public class VoteCountResponse {
    private List<VoteEmprendimientoResponse> votes;
    private int voteCount;

    public List<VoteEmprendimientoResponse> getVotes() {
        return votes;
    }

    public void setVotes(List<VoteEmprendimientoResponse> votes) {
        this.votes = votes;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
