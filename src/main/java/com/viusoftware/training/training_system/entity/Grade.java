package com.viusoftware.training.training_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "grade")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private UsersStudents student;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private Double value;
    private String type; // VD: "Miệng", "15 phút", "1 tiết", "Học kỳ"

    // ...getter/setter...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UsersStudents getStudent() { return student; }
    public void setStudent(UsersStudents student) { this.student = student; }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
