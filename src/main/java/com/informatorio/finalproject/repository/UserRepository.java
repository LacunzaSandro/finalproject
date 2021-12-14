package com.informatorio.finalproject.repository;

import com.informatorio.finalproject.dto.UserLoginDto;
import com.informatorio.finalproject.dto.VoteUserResponse;
import com.informatorio.finalproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

    @Query(value = "SELECT v.id, e.id as emprendimientoId, e.name as emprendimientoName, e.create_at from votes v INNER JOIN emprendimientos e ON e.id = v.emprendimiento_id where v.user_id = ?1", nativeQuery = true)
    List<VoteUserResponse> findVoteOfUser(Long id);
    Optional<User> findUserByEmailAndPassword(String email, String pwd);
    User findUserByEmail(String email);
}
