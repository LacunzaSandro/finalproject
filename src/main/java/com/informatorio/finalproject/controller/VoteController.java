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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class VoteController {
    @Autowired
    private VoteServicie voteServicie;
    @Autowired
    private EmprendimientoService emprendimientoService;
    @Autowired
    private UserService userService;

    @PostMapping("vote")
    public ResponseEntity<?> createVote(@Valid @RequestBody Vote vote) {
        Optional<Emprendimiento> oEmp = emprendimientoService.findById(vote.getEmprendimiento_id());
        Optional<User> oUser = userService.findById(vote.getUser_id());
        if (oEmp.isEmpty() || oUser.isEmpty()) {
            throw new RecordNotFoundException("Record not Found");
        } else if (Objects.isNull(oEmp.get().getEvent())) {
            throw new SimpleException("This Emprendimiento does not have an added event");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(voteServicie.save(vote));
    }

    @GetMapping("vote")
    public ResponseEntity<?> getAllVote() {
        return ResponseEntity.ok(voteServicie.findAll());
    }
}
