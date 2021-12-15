package com.informatorio.finalproject.controller;

import com.informatorio.finalproject.dto.EventDto;
import com.informatorio.finalproject.entity.Emprendimiento;
import com.informatorio.finalproject.entity.Event;
import com.informatorio.finalproject.exception.RecordNotFoundException;
import com.informatorio.finalproject.service.EmprendimientoService;
import com.informatorio.finalproject.service.EventService;
import com.informatorio.finalproject.service.UserService;
import com.informatorio.finalproject.service.VoteServicie;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping
public class EventController {
    @Autowired
    private EmprendimientoService emprendimientoService;
    @Autowired
    private UserService userService;
    @Autowired
    private EventService eventService;
    @Autowired
    private VoteServicie voteServicie;

    //create event
    @PostMapping("event")
    public ResponseEntity<?> createEvent(@Valid @RequestBody Event event) {
        return ResponseEntity.ok(eventService.save(event));
    }
    @PostMapping("event/{eventId}/emprendimiento/{empId}")
    public ResponseEntity<?> createEvent(@RequestParam Long eventId,
                                         @RequestParam Long empId) {
        Optional<Emprendimiento> oEmp = emprendimientoService.findById(empId);
        Optional<Event> oEvent = eventService.findById(eventId);
        if (!oEmp.isPresent() || !oEvent.isPresent()){
            throw new RecordNotFoundException("Record not found");
        }
        oEvent.get().getEmprendimientos().add(oEmp.get());
        return ResponseEntity.ok(eventService.save(oEvent.get()));
    }
    //update event
    @PutMapping("event/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable(value = "id") Long eventId,
                                         @Valid @RequestBody EventDto event) {
        Optional<Event> oEvent = eventService.findById(eventId);
        if (!oEvent.isPresent()){
            throw new RecordNotFoundException("Invalid event id : " + eventId);
        }
        BeanUtils.copyProperties(event,oEvent.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.save(oEvent.get()));
    }
    @DeleteMapping("event/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable(value = "id") Long eventId) {
        Optional<Event> event = eventService.findById(eventId);
        if (event.isEmpty()) {
            throw new RecordNotFoundException("Invalid user id : " + eventId);
        }
        event.get().getEmprendimientos().removeAll(event.get().getEmprendimientos());
        eventService.save(event.get());
        eventService.deleteById(eventId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("event/{id}")
    public ResponseEntity<?> getEventById(@PathVariable  Long id) {
        Optional<Event> oEvent = eventService.findById(id);
        if (!oEvent.isPresent()){
            throw new RecordNotFoundException("Invalid event id : " + id);
        }
        return ResponseEntity.status(HttpStatus.OK).body(oEvent);
    }
    @GetMapping("event")
    public List<Event> getAllEvent() {
        List<Event> eventList = StreamSupport
                .stream(eventService.findAll().spliterator(),false)
                .collect(Collectors.toList());
        return eventList;
    }
    @GetMapping("event/{id}/ranking")
    public ResponseEntity<?> getRankingOfEvent(@PathVariable  Long id) {
        return ResponseEntity.ok(voteServicie.getRankingVotesOnEvent(id));
    }
    //schedule for event closing date review
    @Scheduled(cron = "0 3 22 * * ?")
    public void tarea4() {
        System.out.println("Tarea usando expresiones cron");
    }

}
