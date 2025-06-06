package com.viusoftware.training.training_system.api;

import com.viusoftware.training.training_system.entity.ClassRoom;
import com.viusoftware.training.training_system.repository.ClassRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
public class ClassRoomApiController {
    @Autowired
    private ClassRoomRepository repo;

    @GetMapping
    public List<ClassRoom> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ClassRoom getById(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    @PostMapping
    public ClassRoom create(@RequestBody ClassRoom c) {
        return repo.save(c);
    }

    @PutMapping("/{id}")
    public ClassRoom update(@PathVariable Long id, @RequestBody ClassRoom c) {
        c.setId(id);
        return repo.save(c);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
