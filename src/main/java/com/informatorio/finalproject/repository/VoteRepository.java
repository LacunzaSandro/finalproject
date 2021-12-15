package com.informatorio.finalproject.repository;

import com.informatorio.finalproject.dto.RankingResponse;
import com.informatorio.finalproject.dto.VoteResponse;
import com.informatorio.finalproject.dto.VoteUserResponse;
import com.informatorio.finalproject.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    @Query(value = "SELECT * " +
            "FROM votes v  " +
            "where v.user_id = ?1 " +
            "AND v.emprendimiento_id = ?2", nativeQuery = true)
    Optional<Vote> findVote(Long user_id, Long emp_id);

    @Query(value = "SELECT v.id, e.id as emprendimientoId, e.name as emprendimientoName, u.id as userId, CONCAT(u.first_name, ' ',u.last_name) as fullName, e.create_at " +
            "FROM votes v " +
            "INNER JOIN emprendimientos e " +
            "ON e.id = v.emprendimiento_id " +
            "INNER JOIN users u " +
            "ON u.id = v.user_id"
            , nativeQuery = true)
    List<VoteResponse> findAllVotes();

    @Query(value = "SELECT  e.id, e.name, COUNT(e.name) as votes " +
            "FROM `votes`as v " +
            "INNER JOIN emprendimientos as e" +
            " ON v.emprendimiento_id = e.id " +
            "WHERE e.event_id = ?1 " +
            "GROUP BY v.emprendimiento_id " +
            "ORDER BY COUNT(e.name) DESC;", nativeQuery = true)
    List<RankingResponse> getRankingVotesOnEvent(Long event_id);

}
