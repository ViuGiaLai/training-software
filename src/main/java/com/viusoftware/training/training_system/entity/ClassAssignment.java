package com.viusoftware.training.training_system.entity;

import jakarta.persistence.*;

@Entity
public class ClassAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UsersTeachers teacher;

    @ManyToOne
    private ClassRoom classRoom;

    // ...getter/setter...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UsersTeachers getTeacher() { return teacher; }
    public void setTeacher(UsersTeachers teacher) { this.teacher = teacher; }

    public ClassRoom getClassRoom() { return classRoom; }
    public void setClassRoom(ClassRoom classRoom) { this.classRoom = classRoom; }
}
