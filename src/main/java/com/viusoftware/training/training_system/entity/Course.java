package com.viusoftware.training.training_system.entity;

import jakarta.persistence.*;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Thêm trường name (tên khóa học)
    private String name;

    @ManyToOne
    private ClassRoom classRoom;

    @ManyToOne
    private UsersTeachers teacher;

    private String description;

    // ...getter/setter...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ClassRoom getClassRoom() { return classRoom; }
    public void setClassRoom(ClassRoom classRoom) { this.classRoom = classRoom; }

    public UsersTeachers getTeacher() { return teacher; }
    public void setTeacher(UsersTeachers teacher) { this.teacher = teacher; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // Nếu muốn lấy grade, subject, teacherName, studentCount, periodsPerWeek cho Thymeleaf:
    @Transient
    public String getGrade() {
        return classRoom != null ? classRoom.getGrade() : "";
    }

    @Transient
    public String getSubject() {
        // Nếu có entity Subject, hãy thêm trường subject vào Course và trả về subject.getName()
        return "";
    }

    @Transient
    public String getTeacherName() {
        return teacher != null ? teacher.getFullName() : "";
    }

    @Transient
    public int getStudentCount() {
        return classRoom != null ? classRoom.getStudentCount() : 0;
    }

    @Transient
    public int getPeriodsPerWeek() {
        // Nếu có trường này, trả về số tiết/tuần, nếu không thì return 0 hoặc xử lý ở controller
        return 0;
    }
}