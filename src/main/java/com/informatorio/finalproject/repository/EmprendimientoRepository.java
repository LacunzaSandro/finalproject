package com.informatorio.finalproject.repository;

import com.informatorio.finalproject.dto.VoteEmprendimientoResponse;
import com.informatorio.finalproject.entity.Emprendimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmprendimientoRepository extends JpaRepository<Emprendimiento, Long> {
    @Query(value = "SELECT * FROM emprendimientos e WHERE e.tags LIKE %:tag%", nativeQuery = true)
    List<Emprendimiento> getEmprendimientoByTagLike(String tag);
    List<Emprendimiento> getByPublished(Boolean published);
    @Query(value = "SELECT v.id, u.id as UserId, u.first_name as firstName, u.last_name as lastName, v.create_at from votes v " +
            "INNER JOIN users u " +
            "ON u.id = v.user_id " +
            "WHERE v.emprendimiento_id = ?1", nativeQuery = true)
    List<VoteEmprendimientoResponse> findVoteOfEmprendimiento(Long id);
    @Modifying
    @Query(value = "DELETE FROM emprendimientos e WHERE e.id = ?1", nativeQuery = true)
    void deleteById(Long id);
}
