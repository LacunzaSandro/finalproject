package com.informatorio.finalproject.controller;

import com.informatorio.finalproject.entity.Emprendimiento;
import com.informatorio.finalproject.entity.User;
import com.informatorio.finalproject.entity.Vote;
import com.informatorio.finalproject.exception.RecordNotFoundException;
import com.informatorio.finalproject.exception.SimpleException;
import com.informatorio.finalproject.service.EmprendimientoService;
import com.informatorio.finalproject.service.UserService;
import com.informatorio.finalproject.service.VoteServicie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping
public class VoteController {
    private final VoteServicie voteServicie;
    private final EmprendimientoService emprendimientoService;
    private final UserService userService;
    @Autowired
    public VoteController(VoteServicie voteServicie, EmprendimientoService emprendimientoService, UserService userService) {
        this.voteServicie = voteServicie;
        this.emprendimientoService = emprendimientoService;
        this.userService = userService;
    }

    @PostMapping("vote")
    public ResponseEntity<?> createVote(@Valid @RequestBody Vote vote) {
        //recover authenticate user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.toString();
        User user = userService.findUserByEmail(email);
        //user authenticate vote an emprendimiento. This emprendimiento must be added to an event
        Optional<Emprendimiento> oEmp = emprendimientoService.findById(vote.getEmprendimiento_id());
        if (voteServicie.findVote(user.getId(),oEmp.get().getId()).isPresent()) {
            throw new SimpleException("Only can vote once");
        }
        if (oEmp.isEmpty()) {
            throw new RecordNotFoundException("Emprendimiento not found");
        } else if (Objects.isNull(oEmp.get().getEvent())) {
            throw new SimpleException("This Emprendimiento does not have an added event");
        }
        vote.setUser_id(user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(voteServicie.save(vote));
    }

    @GetMapping("vote")
    public ResponseEntity<?> getAllVote() {
        return ResponseEntity.ok(voteServicie.findAllVotes());
    }
}
