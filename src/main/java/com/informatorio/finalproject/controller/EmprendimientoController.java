package com.informatorio.finalproject.controller;

import com.informatorio.finalproject.entity.Emprendimiento;
import com.informatorio.finalproject.entity.User;
import com.informatorio.finalproject.repository.EmprendimientoRepository;
import com.informatorio.finalproject.repository.UserRepository;
import com.informatorio.finalproject.service.EmprendimientoService;
import com.informatorio.finalproject.service.UserService;
import com.informatorio.finalproject.utils.exception.RecordNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping(value = "/emprendimiento", params = "tag")
    public List<Emprendimiento> filterEmpByTags(@RequestParam String tag) {
        return emprendimientoService.getByTag(tag);
    }
    //list by active emprendimiento
    @GetMapping(value = "/emprendimiento", params = "published")
    public List<Emprendimiento> filterEmpByStateActive(@RequestParam Boolean published) {
        return emprendimientoService.getByPublished(published);
    }
}
