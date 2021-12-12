package com.informatorio.finalproject.repository;

import com.informatorio.finalproject.dto.VoteEmprendimientoResponse;
import com.informatorio.finalproject.dto.VoteUserResponse;
import com.informatorio.finalproject.entity.Emprendimiento;
import com.informatorio.finalproject.entity.Tag;
import com.informatorio.finalproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmprendimientoRepository extends JpaRepository<Emprendimiento, Long> {
    //List<Emprendimiento> getByTag(String tag);
    List<Emprendimiento> getByPublished(Boolean published);
    //@Query(nativeQuery = false,value = "INSERT INTO emprendimiento_tag (fk_emprendimiento, fk_tag) VALUES(?1,?2)")
    //void addTagExisted(Long emp_id, Long tag_id);
    @Query(value = "select * from emprendimiento_tag where fk_emprendimiento = ?1 and fk_tag = ?2", nativeQuery = true)
    Optional<Object> findRelationshipWithTag(Long emp_id, Long tag_id);
    @Query(value = "SELECT v.id, u.id as UserId, u.first_name as firstName, u.last_name as lastName, v.create_at from votes v " +
            "INNER JOIN users u " +
            "ON u.id = v.user_id " +
            "WHERE v.emprendimiento_id = ?1", nativeQuery = true)
    List<VoteEmprendimientoResponse> findVoteOfEmprendimiento(Long id);
}
