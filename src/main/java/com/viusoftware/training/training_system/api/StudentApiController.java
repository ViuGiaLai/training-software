package com.viusoftware.training.training_system.api;

import com.viusoftware.training.training_system.entity.UsersStudents;
import com.viusoftware.training.training_system.repository.UsersStudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentApiController {
    @Autowired
    private UsersStudentsRepository repo;

    @GetMapping
    public List<UsersStudents> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public UsersStudents getById(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    @PostMapping
    public UsersStudents create(@RequestBody UsersStudents s) {
        return repo.save(s);
    }

    @PutMapping("/{id}")
    public UsersStudents update(@PathVariable Long id, @RequestBody UsersStudents s) {
        s.setId(id);
        return repo.save(s);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
