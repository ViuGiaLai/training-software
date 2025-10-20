package com.viusoftware.training.training_system.controllers;

import com.viusoftware.training.training_system.service.UserService;
import com.viusoftware.training.training_system.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import com.viusoftware.training.training_system.entity.UsersAdmin;
import java.security.Principal;
import org.springframework.security.core.Authentication;
import com.viusoftware.training.training_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.viusoftware.training.training_system.entity.ExamSchedule;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final FileStorageService fileStorageService;

    @Autowired
    public AdminController(UserService userService, FileStorageService fileStorageService) {
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    @Autowired
    private UsersStudentsRepository usersStudentsRepository;
    @Autowired
    private UsersTeachersRepository usersTeachersRepository;
    @Autowired
    private UsersAdminRepository usersAdminRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ClassRoomRepository classRoomRepository;
    @Autowired
    private ExamManagementRepository examManagementRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private PeriodRepository periodRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @GetMapping("/dashboard")
    public String showAdminDashboard(Model model, Principal principal) {
        String username = principal.getName();
        UsersAdmin currentUser = userService.findAdminByUsername(username);
        model.addAttribute("currentUser", currentUser);
        return "dashboard/admin"; // Đây là layout tổng thể (admin.html)
    }

    // Các fragment trả về nội dung động, layout tổng thể là admin.html
    @GetMapping("/dashboard/dashboard/fragment")
    public String dashboardFragment(Model model) {
        // Nếu có thống kê, truyền vào model ở đây
        // model.addAttribute("studentCount", ...);
        // model.addAttribute("teacherCount", ...);
        // model.addAttribute("courseCount", ...);
        // model.addAttribute("classCount", ...);
        return "dashboard/dashboard :: content";
    }

    @GetMapping("/dashboard/users/user-student-list/fragment")
    public String userStudentListFragment(Model model) {
        model.addAttribute("students", usersStudentsRepository.findAll());
        model.addAttribute("classrooms", classRoomRepository.findAll());
        model.addAttribute("courses", classRoomRepository.findDistinctAcademicYears());
        // Sửa dòng dưới nếu repository không có findDistinctStatuses()
        // model.addAttribute("statuses", usersStudentsRepository.findDistinctStatuses());
        // Nếu không có method, dùng cứng:
        model.addAttribute("statuses", java.util.List.of("Đang học", "Đã tốt nghiệp", "Bảo lưu", "Bị đình chỉ"));
        // Nếu có filter, truyền selectedClassroom, selectedCourse, selectedStatus
        return "dashboard/users/user-student-list :: content";
    }

    @GetMapping("/dashboard/users/user-teacher-list/fragment")
    public String userTeacherListFragment(Model model) {
        model.addAttribute("teachers", usersTeachersRepository.findAll());
        return "dashboard/users/user-teacher-list :: content";
    }

    @GetMapping("/dashboard/user-list/fragment")
    public String userListFragment(Model model) {
        model.addAttribute("students", usersStudentsRepository.findAll());
        model.addAttribute("teachers", usersTeachersRepository.findAll());
        model.addAttribute("admins", usersAdminRepository.findAll());
        return "dashboard/users/user-list :: content";
    }

    @GetMapping("/dashboard/course-management")
    public String courseManagement(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        model.addAttribute("classRooms", classRoomRepository.findAll());
        model.addAttribute("teachers", usersTeachersRepository.findAll());
        return "dashboard/course-management";
    }

    @GetMapping("/admin/dashboard/course-management/add")
    public String showAddCourseForm(Model model) {
        model.addAttribute("classRooms", classRoomRepository.findAll());
        model.addAttribute("teachers", usersTeachersRepository.findAll());
        return "dashboard/course-management/add-course";
    }

    @PostMapping("/admin/dashboard/course-management/add")
    public String addCourse(@RequestParam String name, @RequestParam Long classRoomId, @RequestParam Long teacherId, @RequestParam String description, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            com.viusoftware.training.training_system.entity.Course course = new com.viusoftware.training.training_system.entity.Course();
            course.setName(name);
            course.setClassRoom(classRoomRepository.findById(classRoomId).orElse(null));
            course.setTeacher(usersTeachersRepository.findById(teacherId).orElse(null));
            course.setDescription(description);
            courseRepository.save(course);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm khóa học thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi thêm khóa học: " + e.getMessage());
        }
        return "redirect:/admin/dashboard#course-management";
    }

    @PostMapping("/admin/dashboard/course-management/edit")
    public String editCourse(@RequestParam Long id, @RequestParam String name, @RequestParam Long classRoomId, @RequestParam Long teacherId, @RequestParam String description, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            com.viusoftware.training.training_system.entity.Course course = courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid course Id:" + id));
            course.setName(name);
            course.setClassRoom(classRoomRepository.findById(classRoomId).orElse(null));
            course.setTeacher(usersTeachersRepository.findById(teacherId).orElse(null));
            course.setDescription(description);
            courseRepository.save(course);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật khóa học thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật khóa học: " + e.getMessage());
        }
        return "redirect:/admin/dashboard#course-management";
    }

    @PostMapping("/admin/dashboard/course-management/delete")
    public String deleteCourse(@RequestParam Long id, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            courseRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa khóa học thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa khóa học: " + e.getMessage());
        }
        return "redirect:/admin/dashboard#course-management";
    }

    @GetMapping("/dashboard/exam-management/fragment")
    public String examManagementFragment(
        @RequestParam(required = false) String search,
        @RequestParam(required = false) String subjectFilter,
        @RequestParam(required = false) String classFilter,
        @RequestParam(required = false) String roomFilter,
        Model model) {
        // Lọc dữ liệu nếu có filter, nếu không thì lấy tất cả
        List<ExamSchedule> examSchedules;
        if (search != null && !search.isBlank()) {
            examSchedules = examManagementRepository.findBySubjectContaining(search);
        } else if (subjectFilter != null && !subjectFilter.isBlank()) {
            examSchedules = examManagementRepository.findBySubjectContaining(subjectFilter);
        } else if (classFilter != null && !classFilter.isBlank()) {
            examSchedules = examManagementRepository.findByClassesContaining(classFilter);
        } else if (roomFilter != null && !roomFilter.isBlank()) {
            // Nếu cần filter theo phòng, hãy thêm method vào repository
            examSchedules = examManagementRepository.findAll().stream()
                .filter(e -> e.getRooms() != null && e.getRooms().contains(roomFilter))
                .toList();
        } else {
            examSchedules = examManagementRepository.findAll();
        }

        model.addAttribute("examSchedules", examSchedules);
        model.addAttribute("totalExams", examSchedules.size());
        model.addAttribute("classRooms", classRoomRepository.findAll());
        model.addAttribute("rooms", roomRepository.findAll());
        model.addAttribute("subjects", subjectRepository.findAll());
        model.addAttribute("subjectFilter", subjectFilter);
        model.addAttribute("classFilter", classFilter);
        model.addAttribute("roomFilter", roomFilter);
        model.addAttribute("search", search);
        return "dashboard/exam-management :: content";
    }

    @GetMapping("/dashboard/schedule-management/fragment")
    public String scheduleManagementFragment(
        @RequestParam(required = false) Long classId,
        @RequestParam(required = false) String weekStart,
        Model model) {
        java.util.List classes = classRoomRepository.findAll();
        java.util.List subjects = subjectRepository.findAll();
        java.util.List teachers = usersTeachersRepository.findAll();
        java.util.List rooms = roomRepository.findAll();
        java.util.List periods = periodRepository.findAll();

        model.addAttribute("classes", classes != null ? classes : java.util.Collections.emptyList());
        model.addAttribute("subjects", subjects != null ? subjects : java.util.Collections.emptyList());
        model.addAttribute("teachers", teachers != null ? teachers : java.util.Collections.emptyList());
        model.addAttribute("rooms", rooms != null ? rooms : java.util.Collections.emptyList());
        model.addAttribute("periods", periods != null ? periods : java.util.Collections.emptyList());
        model.addAttribute("selectedSubjectId", null);

        java.time.LocalDate startDate;
        if (weekStart != null && !weekStart.isBlank()) {
            startDate = java.time.LocalDate.parse(weekStart);
        } else {
            java.time.LocalDate today = java.time.LocalDate.now();
            startDate = today.with(java.time.DayOfWeek.MONDAY);
        }
        java.time.LocalDate endDate = startDate.plusDays(6);
        model.addAttribute("weekStart", startDate);
        model.addAttribute("weekEnd", endDate);

        java.util.List schedules = (classId != null)
                ? scheduleRepository.findByClassRoomIdAndDateBetween(classId, startDate, endDate)
                : java.util.Collections.emptyList();
        model.addAttribute("selectedClassId", classId);
        model.addAttribute("schedules", schedules);

        return "dashboard/schedule-management :: content";
    }

    @GetMapping("/dashboard/classroom")
    public String classroomPage(
            Model model,
            @RequestParam(required = false) String grade,
            @RequestParam(required = false) String academicYear,
            @RequestParam(required = false) Long teacherId
    ) {
        java.util.List<com.viusoftware.training.training_system.entity.ClassRoom> classRooms = classRoomRepository.findAllWithStudents();
        if (grade != null && !grade.isBlank()) {
            classRooms = classRooms.stream()
                .filter(c -> grade.equals(c.getGrade() != null ? c.getGrade() : ""))
                .collect(java.util.stream.Collectors.toList());
        }
        if (academicYear != null && !academicYear.isBlank()) {
            classRooms = classRooms.stream()
                .filter(c -> academicYear.equals(c.getAcademicYear() != null ? c.getAcademicYear() : ""))
                .collect(java.util.stream.Collectors.toList());
        }
        if (teacherId != null) {
            classRooms = classRooms.stream()
                .filter(c -> c.getHomeroomTeacher() != null && teacherId.equals(c.getHomeroomTeacher().getId()))
                .collect(java.util.stream.Collectors.toList());
        }
        // Chuẩn bị map classId -> sorted students
        java.util.Map<Long, java.util.List<com.viusoftware.training.training_system.entity.UsersStudents>> sortedStudentsMap = new java.util.HashMap<>();
        for (com.viusoftware.training.training_system.entity.ClassRoom cl : classRooms) {
            java.util.List<com.viusoftware.training.training_system.entity.UsersStudents> sortedStudents = classRoomRepository.findStudentsByClassRoomOrderByFullName(cl.getId());
            sortedStudentsMap.put(cl.getId(), sortedStudents);
        }

        java.util.List<String> grades = classRoomRepository.findDistinctGrades();
        java.util.List<String> academicYears = classRoomRepository.findDistinctAcademicYears();
        java.util.List<com.viusoftware.training.training_system.entity.UsersTeachers> teachers = usersTeachersRepository.findAll();

        model.addAttribute("classRooms", classRooms);
        model.addAttribute("sortedStudentsMap", sortedStudentsMap);
        model.addAttribute("grades", grades);
        model.addAttribute("academicYears", academicYears);
        model.addAttribute("teachers", teachers);
        model.addAttribute("selectedGrade", grade);
        model.addAttribute("selectedYear", academicYear);
        model.addAttribute("selectedTeacherId", teacherId);

        return "dashboard/classroom";
    }

    @PostMapping("/dashboard/classroom/add")
    public String addClassRoom(
            @RequestParam String code,
            @RequestParam String name,
            @RequestParam String grade,
            @RequestParam String academicYear,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Long homeroomTeacherId,
            org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes
    ) {
        try {
            com.viusoftware.training.training_system.entity.ClassRoom classRoom = new com.viusoftware.training.training_system.entity.ClassRoom();
            classRoom.setCode(code);
            classRoom.setName(name);
            classRoom.setGrade(grade);
            classRoom.setAcademicYear(academicYear);
            classRoom.setDescription(description);
            if (homeroomTeacherId != null) {
                com.viusoftware.training.training_system.entity.UsersTeachers teacher = usersTeachersRepository.findById(homeroomTeacherId).orElse(null);
                classRoom.setHomeroomTeacher(teacher);
            } else {
                classRoom.setHomeroomTeacher(null);
            }
            classRoomRepository.save(classRoom);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm lớp thành công!");
            return "redirect:/admin/dashboard/classroom";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/admin/dashboard/classroom";
        }
    }

    @PostMapping("/dashboard/classroom/edit")
    public String editClassRoom(
            @RequestParam Long id,
            @RequestParam String code,
            @RequestParam String name,
            @RequestParam String grade,
            @RequestParam String academicYear,
            @RequestParam(required = false) Long homeroomTeacherId,
            @RequestParam(required = false) String description
    ) {
        com.viusoftware.training.training_system.entity.ClassRoom classRoom = classRoomRepository.findById(id).orElse(null);
        if (classRoom != null) {
            classRoom.setCode(code);
            classRoom.setName(name);
            classRoom.setGrade(grade);
            classRoom.setAcademicYear(academicYear);
            classRoom.setDescription(description);
            if (homeroomTeacherId != null) {
                com.viusoftware.training.training_system.entity.UsersTeachers teacher = usersTeachersRepository.findById(homeroomTeacherId).orElse(null);
                classRoom.setHomeroomTeacher(teacher);
            } else {
                classRoom.setHomeroomTeacher(null);
            }
            classRoomRepository.save(classRoom);
        }
        return "redirect:/admin/dashboard/classroom";
    }

    @GetMapping("/dashboard/class-assignment/fragment")
    public String classAssignmentFragment(Model model) {
        model.addAttribute("teachers", usersTeachersRepository.findAll());
        model.addAttribute("classRooms", classRoomRepository.findAll());
        // Nếu có bảng assignment, truyền assignments
        // model.addAttribute("assignments", assignmentRepository.findAll());
        model.addAttribute("grades", classRoomRepository.findDistinctGrades());
        return "dashboard/class-assignment :: content";
    }

    @GetMapping("/create-user")
    public String showCreateUserForm(Model model) {
        // Add any necessary model attributes for the form (e.g., list of roles)
        return "dashboard/create-user"; // This will render templates/dashboard/create-user.html
    }

    @PostMapping(value = "/create-user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("role") String role,
            @RequestParam("email") String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            Model model) {

        String imageUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            // TODO: Implement image file saving logic here
            // For now, let's assume a placeholder URL
            // imageUrl = "/images/" + imageFile.getOriginalFilename(); // Example URL
            try {
                imageUrl = fileStorageService.storeFile(imageFile); // Use FileStorageService to store file
            } catch (RuntimeException e) {
                 model.addAttribute("errorMessage", "Failed to upload image: " + e.getMessage());
                 // Keep the entered values in case of error
                 model.addAttribute("username", username);
                 model.addAttribute("email", email);
                 model.addAttribute("phone", phone);
                 // Optionally, add back other model attributes needed for the form
                 return "dashboard/create-user";
            }
        }

        try {
            userService.createUser(username, password, role, email, phone, imageUrl);
            model.addAttribute("successMessage", "User created successfully!");
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            // Keep the entered values in case of error
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            model.addAttribute("phone", phone);
            // Optionally, add back other model attributes needed for the form
            return "dashboard/create-user";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            // Keep the entered values in case of error
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            model.addAttribute("phone", phone);
            // Optionally, add back other model attributes needed for the form
            return "dashboard/create-user";
        }

        return "redirect:/admin/create-user"; // Redirect back to the form with success message
    }

    @GetMapping("/users/{username}")
    @ResponseBody
    public UsersAdmin getUserByUsername(@PathVariable("username") String username) {
        // TODO: Implement logic in UserService to find user by username
        return userService.findAdminByUsername(username); // Assuming this method exists in UserService
    }

    // New endpoint to update admin user profile
    @PostMapping(value = "/update-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody // Indicate that the return value should be the response body
    public String updateAdminProfile(
            @RequestParam("id") Long id,
            @RequestParam("email") String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            Principal principal) { // To verify the user is updating their own profile

        String username = principal.getName();
        // Optional: Add verification to ensure the user ID being updated matches the authenticated user
        UsersAdmin currentUser = userService.findAdminByUsername(username);
        if (currentUser == null || !currentUser.getId().equals(id)) {
             // Or throw an exception, depending on desired error handling
             return "Error: Unauthorized to update this profile.";
        }

        String imageUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                imageUrl = fileStorageService.storeFile(imageFile); // Store the new image
            } catch (RuntimeException e) {
                 return "Error uploading image: " + e.getMessage();
            }
        }

        try {
            // TODO: Implement logic in UserService to update admin user
            userService.updateAdminUser(id, email, phone, imageUrl); // Assuming this method exists in UserService
            return "Profile updated successfully!";
        } catch (Exception e) {
            // Log the error properly in a real application
            return "Error updating profile: " + e.getMessage();
        }
    }

    // You might want to add more admin related endpoints here (e.g., view users, edit users, delete users)
}