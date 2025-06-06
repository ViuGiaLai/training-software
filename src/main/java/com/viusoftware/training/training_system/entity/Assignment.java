package com.viusoftware.training.training_system.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "assignment")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "class_room_id")
    private ClassRoom classRoom;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private UsersTeachers teacher;

    private LocalDate dueDate;

    // ...getter/setter...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ClassRoom getClassRoom() { return classRoom; }
    public void setClassRoom(ClassRoom classRoom) { this.classRoom = classRoom; }

    public UsersTeachers getTeacher() { return teacher; }
    public void setTeacher(UsersTeachers teacher) { this.teacher = teacher; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
}
