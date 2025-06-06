package com.viusoftware.training.training_system.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private UsersStudents student;

    @ManyToOne
    @JoinColumn(name = "class_room_id")
    private ClassRoom classRoom;

    private LocalDate date;
    private boolean present; // true: có mặt, false: vắng

    // ...getter/setter...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UsersStudents getStudent() { return student; }
    public void setStudent(UsersStudents student) { this.student = student; }

    public ClassRoom getClassRoom() { return classRoom; }
    public void setClassRoom(ClassRoom classRoom) { this.classRoom = classRoom; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public boolean isPresent() { return present; }
    public void setPresent(boolean present) { this.present = present; }
}
