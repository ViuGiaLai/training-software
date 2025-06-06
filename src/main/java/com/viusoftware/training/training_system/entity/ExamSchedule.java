package com.viusoftware.training.training_system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class ExamSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String rooms;
    private String classes;
    private Integer numberOfStudents;
    private String status;

    public ExamSchedule() {}

    public ExamSchedule(String subject, LocalDateTime startTime, LocalDateTime endTime,
                        String rooms, String classes, Integer numberOfStudents, String status) {
        this.subject = subject;
        this.startTime = startTime;
        this.endTime = endTime;
        this.rooms = rooms;
        this.classes = classes;
        this.numberOfStudents = numberOfStudents;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public String getRooms() { return rooms; }
    public void setRooms(String rooms) { this.rooms = rooms; }

    public String getClasses() { return classes; }
    public void setClasses(String classes) { this.classes = classes; }

    public Integer getNumberOfStudents() { return numberOfStudents; }
    public void setNumberOfStudents(Integer numberOfStudents) { this.numberOfStudents = numberOfStudents; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
