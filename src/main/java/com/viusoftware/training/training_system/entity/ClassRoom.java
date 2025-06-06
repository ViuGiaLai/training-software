package com.viusoftware.training.training_system.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "class_room")
public class ClassRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Bổ sung trường code và description nếu chưa có
    private String code;
    private String description;

    // Bổ sung trường grade nếu chưa có
    private String grade;

    // Bổ sung trường năm học
    private String academicYear;

    // GVCN
    @ManyToOne
    @JoinColumn(name = "homeroom_teacher_id")
    private UsersTeachers homeroomTeacher;

    // Danh sách học sinh
    @OneToMany(mappedBy = "classRoom", fetch = FetchType.LAZY)
    private List<UsersStudents> students;

    // ...getter/setter...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // Bổ sung getter/setter cho grade
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }

    public UsersTeachers getHomeroomTeacher() { return homeroomTeacher; }
    public void setHomeroomTeacher(UsersTeachers homeroomTeacher) { this.homeroomTeacher = homeroomTeacher; }

    public List<UsersStudents> getStudents() { return students; }
    public void setStudents(List<UsersStudents> students) { this.students = students; }

    // Thêm getter cho studentCount để Thymeleaf không lỗi
    public int getStudentCount() {
        return students != null ? students.size() : 0;
    }
}
