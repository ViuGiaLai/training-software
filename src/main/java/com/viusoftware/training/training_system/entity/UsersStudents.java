package com.viusoftware.training.training_system.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users_students")
public class UsersStudents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String studentCode;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;
    private String gender;
    private String major;
    private String course;
    private String classroom;
    private String status;
    private LocalDate enrollmentDate;
    @Column(nullable = false)
    private String password;
    private String role;

    private LocalDate dateOfBirth;
    private String address;
    private String avatarUrl;

    private String birthPlace;
    private String identityNumber;
    private LocalDate identityIssueDate;
    private String identityIssuePlace;
    private String ethnicity;
    private String religion;
    private String nationality;

    private String fatherName;
    private String fatherJob;
    private String fatherPhone;
    private String motherName;
    private String motherJob;
    private String motherPhone;

    // Học lực và hạnh kiểm
    private String academicPerformance;
    private String conduct;

    @ManyToOne
    @JoinColumn(name = "class_room_id")
    private ClassRoom classRoom;

    // Constructors
    public UsersStudents() {}

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public String getClassroom() { return classroom; }
    public void setClassroom(String classroom) { this.classroom = classroom; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public String getBirthPlace() { return birthPlace; }
    public void setBirthPlace(String birthPlace) { this.birthPlace = birthPlace; }

    public String getIdentityNumber() { return identityNumber; }
    public void setIdentityNumber(String identityNumber) { this.identityNumber = identityNumber; }

    public LocalDate getIdentityIssueDate() { return identityIssueDate; }
    public void setIdentityIssueDate(LocalDate identityIssueDate) { this.identityIssueDate = identityIssueDate; }

    public String getIdentityIssuePlace() { return identityIssuePlace; }
    public void setIdentityIssuePlace(String identityIssuePlace) { this.identityIssuePlace = identityIssuePlace; }

    public String getEthnicity() { return ethnicity; }
    public void setEthnicity(String ethnicity) { this.ethnicity = ethnicity; }

    public String getReligion() { return religion; }
    public void setReligion(String religion) { this.religion = religion; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }

    public String getFatherName() { return fatherName; }
    public void setFatherName(String fatherName) { this.fatherName = fatherName; }

    public String getFatherJob() { return fatherJob; }
    public void setFatherJob(String fatherJob) { this.fatherJob = fatherJob; }

    public String getFatherPhone() { return fatherPhone; }
    public void setFatherPhone(String fatherPhone) { this.fatherPhone = fatherPhone; }

    public String getMotherName() { return motherName; }
    public void setMotherName(String motherName) { this.motherName = motherName; }

    public String getMotherJob() { return motherJob; }
    public void setMotherJob(String motherJob) { this.motherJob = motherJob; }

    public String getMotherPhone() { return motherPhone; }
    public void setMotherPhone(String motherPhone) { this.motherPhone = motherPhone; }

    public String getAcademicPerformance() { return academicPerformance; }
    public void setAcademicPerformance(String academicPerformance) { this.academicPerformance = academicPerformance; }

    public String getConduct() { return conduct; }
    public void setConduct(String conduct) { this.conduct = conduct; }

    public ClassRoom getClassRoom() { return classRoom; }
    public void setClassRoom(ClassRoom classRoom) { this.classRoom = classRoom; }
}
