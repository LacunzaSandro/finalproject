package com.informatorio.finalproject.controller;

import com.informatorio.finalproject.entity.Emprendimiento;
import com.informatorio.finalproject.entity.Tag;
import com.informatorio.finalproject.entity.User;
import com.informatorio.finalproject.exception.SimpleException;
import com.informatorio.finalproject.service.EmprendimientoService;
import com.informatorio.finalproject.service.TagService;
import com.informatorio.finalproject.service.UserService;
import com.informatorio.finalproject.exception.RecordNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api")
public class EmprendimientoController {
    @Autowired
    private EmprendimientoService emprendimientoService;
    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;
    // create emprendimiento
    @PostMapping("/users/{id}/emprendimientos")
    public ResponseEntity<?> crearEmprendimiento(
            @PathVariable("id") Long id,
            @RequestBody @Valid Emprendimiento emprendimiento) {
        User user = userService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.getEmprendimientos().add(emprendimiento);
        emprendimiento.setOwner(user);
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }
    @PutMapping("emprendimiento/{id}/tag")
    public ResponseEntity<?> assignTags(@PathVariable("id") Long id,
                                        @RequestBody @Valid Tag tag) {
        Optional<Emprendimiento> oEmp = emprendimientoService.findById(id);
        if (!oEmp.isPresent()) {
            throw new RecordNotFoundException("Invalid user id : " + id);
        }
        Optional<Tag> oTag = tagService.getByText(tag.getText());
        System.out.println("---------------------------------- " + oTag);
        if (!oTag.isPresent()) {
            oEmp.get().addTags(tag);
            System.out.println("----------------------- es presente");
            return ResponseEntity.status(HttpStatus.CREATED).body(emprendimientoService.save(oEmp.get()));
        } else {
            System.out.println("----------------------- NO es presente");
            //oEmp.get().setTags();
            if (!emprendimientoService.findRelationshipWithTag(oEmp.get().getId(),oTag.get().getId())) {
                emprendimientoService.addTagExisting(oEmp.get().getId(), oTag.get().getId());
                oEmp.get().getTags().add(oTag.get());
                return ResponseEntity.status(HttpStatus.CREATED).body(oEmp.get());
            }
            throw new SimpleException("this relationship already existed");
        }
    }
    //update emprendimiento
    @PutMapping("/emprendimientos/{id}")
    public ResponseEntity<?> updateEmprendimiento(@PathVariable("id") Long id,
                                                  @RequestBody @Valid Emprendimiento emp) {
        Optional<Emprendimiento> oEmp = emprendimientoService.findById(id);
        if (!oEmp.isPresent()){
            throw new RecordNotFoundException("Invalid user id : " + id);
        }
        BeanUtils.copyProperties(emp,oEmp.get(),"id");
        return ResponseEntity.status(HttpStatus.CREATED).body(emprendimientoService.save(oEmp.get()));
    }

    @GetMapping("/emprendimiento")
    public List<Emprendimiento> getAllEmprendimiento() {
        List<Emprendimiento> empList = StreamSupport
                .stream(emprendimientoService.findAll().spliterator(),false)
                .collect(Collectors.toList());
        return  empList;
    }
    //list emprendimiento by tags
    /*@GetMapping(value = "/emprendimiento", params = "tag")
    public List<Emprendimiento> filterEmpByTags(@RequestParam String tag) {
        return emprendimientoService.getByTag(tag);
    }*/
    //list by active emprendimiento
    @GetMapping(value = "/emprendimiento", params = "published")
    public List<Emprendimiento> filterEmpByStateActive(@RequestParam Boolean published) {
        return emprendimientoService.getByPublished(published);
    }
    //get user trough multiple params
    /*

     */
    @GetMapping("/emprendimiento/matching")
    public List<Emprendimiento> getMatchingUsers(@RequestBody Emprendimiento emp) {
        return emprendimientoService.findAll(Example.of(emp));
    }
}
