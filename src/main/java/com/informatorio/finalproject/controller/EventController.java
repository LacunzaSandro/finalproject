package com.informatorio.finalproject.controller;

import com.informatorio.finalproject.dto.EventDto;
import com.informatorio.finalproject.entity.Emprendimiento;
import com.informatorio.finalproject.entity.Event;
import com.informatorio.finalproject.entity.User;
import com.informatorio.finalproject.exception.RecordNotFoundException;
import com.informatorio.finalproject.service.EmprendimientoService;
import com.informatorio.finalproject.service.EventService;
import com.informatorio.finalproject.service.TagService;
import com.informatorio.finalproject.service.UserService;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/")
public class EventController {
    @Autowired
    private EmprendimientoService emprendimientoService;
    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;
    @Autowired
    private EventService eventService;

    //create event
    @PostMapping("event")
    public ResponseEntity<?> createEvent(@RequestBody Event event) {
        System.out.println(eventService.save(event));
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
    public ResponseEntity<?> updateEvent(@RequestParam(value = "id") Long eventId, @RequestBody Event event) {
        Optional<Event> oEvent = eventService.findById(eventId);
        if (!oEvent.isPresent()){
            throw new RecordNotFoundException("Invalid event id : " + eventId);
        }
        BeanUtils.copyProperties(event,oEvent.get(),"id","create_at");
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.save(oEvent.get()));
    }
    @DeleteMapping("event/{id}")
    public ResponseEntity<?> deleteEvent(@RequestParam(value = "id") Long eventId) {
        if (!userService.findById(eventId).isPresent()) {
            throw new RecordNotFoundException("Invalid user id : " + eventId);
        }
        userService.deleteById(eventId);
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

}
