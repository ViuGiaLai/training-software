package com.viusoftware.training.training_system.api;

import com.viusoftware.training.training_system.entity.UsersTeachers;
import com.viusoftware.training.training_system.repository.UsersTeachersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherApiController {
    @Autowired
    private UsersTeachersRepository repo;

    @GetMapping
    public List<UsersTeachers> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public UsersTeachers getById(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    @PostMapping
    public UsersTeachers create(@RequestBody UsersTeachers t) {
        return repo.save(t);
    }

    @PutMapping("/{id}")
    public UsersTeachers update(@PathVariable Long id, @RequestBody UsersTeachers t) {
        t.setId(id);
        return repo.save(t);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
