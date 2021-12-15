package com.informatorio.finalproject.controller;

import com.informatorio.finalproject.dto.EmprendimientoDto;
import com.informatorio.finalproject.dto.VoteCountResponse;
import com.informatorio.finalproject.dto.VoteEmprendimientoResponse;
import com.informatorio.finalproject.entity.Emprendimiento;
import com.informatorio.finalproject.entity.Event;
import com.informatorio.finalproject.entity.User;
import com.informatorio.finalproject.exception.SimpleException;
import com.informatorio.finalproject.service.EmprendimientoService;
import com.informatorio.finalproject.service.EventService;
import com.informatorio.finalproject.service.UserService;
import com.informatorio.finalproject.exception.RecordNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping
public class EmprendimientoController {
    @Autowired
    private EmprendimientoService emprendimientoService;
    @Autowired
    private UserService userService;
    @Autowired
    private EventService eventService;
    // create emprendimiento
    @PostMapping("emprendimientos")
    public ResponseEntity<?> crearEmprendimiento(@RequestBody @Valid EmprendimientoDto emp) {
        //validate emprendimiento
        if (emp.isPublished()) {
            if (EmprendimientoDto.analyze(emp)) {
                throw new SimpleException("exist null or empty atributes");
            }
        }
        //recover authenticate user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.toString();
        User user = userService.findUserByEmail(email);
        //convert dto to entity
        Emprendimiento emprendimiento = new Emprendimiento();
        BeanUtils.copyProperties(emp,emprendimiento);
        user.getEmprendimientos().add(emprendimiento);
        emprendimiento.setOwner(user);
        return new ResponseEntity(userService.save(user), HttpStatus.CREATED);
    }
    //relation entity emprendimiento with event
    @PostMapping("emprendimiento/{empId}/event/{eventId}")
    public ResponseEntity<?> enrollToEvent(@PathVariable("empId") Long empId,
                                           @PathVariable("eventId") Long eventId) {
        Optional<Emprendimiento> oEmp = emprendimientoService.findById(empId);
        Optional<Event> oEvent = eventService.findById(eventId);
        if (!oEmp.isPresent() || !oEvent.isPresent()) {
            throw new RecordNotFoundException("One or all entities don't exist");
        }
        oEmp.get().setEvent(oEvent.get());
        emprendimientoService.save(oEmp.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(oEvent.get());
    }

    //update emprendimiento
    @PutMapping("/emprendimiento/{id}")
    public ResponseEntity<?> updateEmprendimiento(@PathVariable("id") Long id,
                                                  @RequestBody @Valid EmprendimientoDto emp) {
        //validate emprendimiento
        if (emp.isPublished()) {
            if (EmprendimientoDto.analyze(emp)) {
                throw new SimpleException("exist null or empty atributes");
            }
        }
        Optional<Emprendimiento> oEmp = emprendimientoService.findById(id);
        if (oEmp.isEmpty()){
            throw new RecordNotFoundException("Invalid user id : " + id);
        }
        //properties copied
        BeanUtils.copyProperties(emp,oEmp.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(emprendimientoService.save(oEmp.get()));
    }

    @GetMapping("/emprendimiento")
    public List<Emprendimiento> getAllEmprendimiento() {
        List<Emprendimiento> empList = StreamSupport
                .stream(emprendimientoService.findAll().spliterator(),false)
                .collect(Collectors.toList());
        return  empList;
    }
    @GetMapping("/emprendimiento/{id}")
    public Optional<Emprendimiento> getEmprendimientoById(@PathVariable("id") Long id) {
        return  emprendimientoService.findById(id);
    }
    //delete emprendimiento
    @DeleteMapping("/emprendimiento/{id}")
    public ResponseEntity<?> deleteEmprendimiento(@PathVariable("id") Long id) {
        if (emprendimientoService.findById(id).isEmpty()) {
            throw new RecordNotFoundException("Invalid user id : " + id);
        }
        emprendimientoService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    //list emprendimiento by tags
    @GetMapping(value = "/emprendimiento", params = "tag")
    public List<Emprendimiento> getEmprendimientoByTagLike(@RequestParam String tag) {
        return emprendimientoService.getEmprendimientoByTagLike(tag);
    }
    //list by active emprendimiento
    @GetMapping(value = "/emprendimiento", params = "published")
    public List<Emprendimiento> filterEmpByStateActive(@RequestParam Boolean published) {
        return emprendimientoService.getByPublished(published);
    }
    //filter by date of user create or city.
    @PostMapping("emprendimiento/search")
    public List<Emprendimiento> searchByDateCreate(@RequestParam( required = false) String tag,
                                         @RequestParam(required = false) String published) {
        List<Emprendimiento> listEmprendimiento = StreamSupport
                .stream(emprendimientoService.findAll(tag, published).spliterator(),false)
                .collect(Collectors.toList());;
        return listEmprendimiento;
    }
    //get user trough multiple params

    /**
     *
     * @param emp
     * the emp entity must have the attributes and value to search
     * Ex:
     * {
     *  "published":true
     * }
     * @return
     */
    @GetMapping("/emprendimiento/matching")
    public List<Emprendimiento> getMatchingUsers(@RequestBody Emprendimiento emp) {
        return emprendimientoService.findAll(Example.of(emp));
    }
    //get all vote of an emprendimiento
    @GetMapping("emprendimiento/{id}/vote")
    public ResponseEntity<?> getAllVoteOfAnEmprendimiento(@PathVariable(value = "id") Long userId) {
        Optional<Emprendimiento> oEmp = emprendimientoService.findById(userId);
        if (!oEmp.isPresent()){
            throw new RecordNotFoundException("Invalid user id : " + userId);
        }
        List<VoteEmprendimientoResponse> voteEmprendimiento = emprendimientoService.findVoteOfEmprendimiento(userId);
        VoteCountResponse vCount = new VoteCountResponse();
        vCount.setVotes(voteEmprendimiento);
        vCount.setVoteCount(voteEmprendimiento.size());
        return ResponseEntity.ok(vCount);
    }

}
