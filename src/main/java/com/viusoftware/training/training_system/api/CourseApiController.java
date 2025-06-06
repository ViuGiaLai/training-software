package com.viusoftware.training.training_system.api;

import com.viusoftware.training.training_system.entity.Course;
import com.viusoftware.training.training_system.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseApiController {
    @Autowired
    private CourseRepository repo;

    @GetMapping
    public List<Course> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Course getById(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    @PostMapping
    public Course create(@RequestBody Course c) {
        return repo.save(c);
    }

    @PutMapping("/{id}")
    public Course update(@PathVariable Long id, @RequestBody Course c) {
        c.setId(id);
        return repo.save(c);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
