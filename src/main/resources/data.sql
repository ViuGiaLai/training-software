-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th6 06, 2025 lúc 04:58 PM
-- Phiên bản máy phục vụ: 9.2.0
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `training_system`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `assignment`
--

CREATE TABLE `assignment` (
  `id` bigint NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `due_date` date DEFAULT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `class_room_id` bigint DEFAULT NULL,
  `teacher_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `attendance`
--

CREATE TABLE `attendance` (
  `id` bigint NOT NULL,
  `date` date DEFAULT NULL,
  `present` bit(1) NOT NULL,
  `class_room_id` bigint DEFAULT NULL,
  `student_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `class_assignment`
--

CREATE TABLE `class_assignment` (
  `id` bigint NOT NULL,
  `class_room_id` bigint DEFAULT NULL,
  `teacher_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `class_room`
--

CREATE TABLE `class_room` (
  `id` bigint NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `student_count` int DEFAULT NULL,
  `grade` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `subject` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `academic_year` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `homeroom_teacher_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `class_room`
--

INSERT INTO `class_room` (`id`, `name`, `student_count`, `grade`, `subject`, `academic_year`, `code`, `description`, `homeroom_teacher_id`) VALUES
(2, '12A1', NULL, '12', NULL, '2023-2025', '760616', '', 4),
(3, '12A2', NULL, '12', NULL, '2023-2028', '00920', '', 3);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `course`
--

CREATE TABLE `course` (
  `id` bigint NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `class_room_id` bigint DEFAULT NULL,
  `teacher_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `courses`
--

CREATE TABLE `courses` (
  `id` bigint NOT NULL,
  `course_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `teacher_id` bigint DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `grade` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `periods_per_week` int DEFAULT NULL,
  `student_count` int DEFAULT NULL,
  `subject` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `teacher_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `class_room_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `degrees`
--

CREATE TABLE `degrees` (
  `id` bigint NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `departments`
--

CREATE TABLE `departments` (
  `id` bigint NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `exam_schedule`
--

CREATE TABLE `exam_schedule` (
  `id` bigint NOT NULL,
  `classes` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `end_time` datetime(6) DEFAULT NULL,
  `number_of_students` int DEFAULT NULL,
  `rooms` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `start_time` datetime(6) DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `subject` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `exam_schedule`
--

INSERT INTO `exam_schedule` (`id`, `classes`, `end_time`, `number_of_students`, `rooms`, `start_time`, `status`, `subject`) VALUES
(1, '12A1', '2025-06-07 14:14:00.000000', 3, 'Phòng 202', '2025-06-07 09:14:00.000000', 'Chờ thi', 'Văn');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `grade`
--

CREATE TABLE `grade` (
  `id` bigint NOT NULL,
  `type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `value` double DEFAULT NULL,
  `student_id` bigint DEFAULT NULL,
  `subject_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `period`
--

CREATE TABLE `period` (
  `id` bigint NOT NULL,
  `end_time` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `start_time` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `period`
--

INSERT INTO `period` (`id`, `end_time`, `name`, `start_time`) VALUES
(1, '07:45', 'Tiết 1', '07:00'),
(2, '08:35', 'Tiết 2', '07:50'),
(3, '09:25', 'Tiết 3', '08:40'),
(4, '10:25', 'Tiết 4', '09:40'),
(5, '11:15', 'Tiết 5', '10:30'),
(6, '13:45', 'Tiết 6', '13:00'),
(7, '14:35', 'Tiết 7', '13:50'),
(8, '15:25', 'Tiết 8', '14:40'),
(9, '16:25', 'Tiết 9', '15:40'),
(10, '17:15', 'Tiết 10', '16:30');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `positions`
--

CREATE TABLE `positions` (
  `id` bigint NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `room`
--

CREATE TABLE `room` (
  `id` bigint NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `room`
--

INSERT INTO `room` (`id`, `name`) VALUES
(101, 'Phòng 101'),
(102, 'Phòng 102'),
(103, 'Phòng 103'),
(104, 'Phòng 104'),
(105, 'Phòng 105'),
(201, 'Phòng 201'),
(202, 'Phòng 202'),
(203, 'Phòng 203'),
(204, 'Phòng 204'),
(205, 'Phòng 205'),
(301, 'Phòng 301'),
(302, 'Phòng 302'),
(303, 'Phòng 303'),
(304, 'Phòng 304'),
(305, 'Phòng 305'),
(401, 'Phòng 401'),
(402, 'Phòng 402'),
(403, 'Phòng 403'),
(404, 'Phòng 404'),
(405, 'Phòng 405'),
(501, 'Phòng 501'),
(502, 'Phòng 502'),
(503, 'Phòng 503'),
(504, 'Phòng 504'),
(505, 'Phòng 505');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `schedule`
--

CREATE TABLE `schedule` (
  `id` bigint NOT NULL,
  `date` date NOT NULL,
  `class_id` bigint NOT NULL,
  `period_id` bigint NOT NULL,
  `room_id` bigint NOT NULL,
  `subject_id` bigint NOT NULL,
  `teacher_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `schedule`
--

INSERT INTO `schedule` (`id`, `date`, `class_id`, `period_id`, `room_id`, `subject_id`, `teacher_id`) VALUES
(1, '2025-06-04', 2, 3, 203, 3, 3),
(2, '2025-06-06', 2, 5, 303, 6, 4),
(3, '2025-06-06', 3, 1, 204, 10, 4);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `subject`
--

CREATE TABLE `subject` (
  `id` bigint NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `subject`
--

INSERT INTO `subject` (`id`, `name`) VALUES
(1, 'Toán'),
(2, 'Văn'),
(3, 'Anh'),
(4, 'Lý'),
(5, 'Hóa'),
(6, 'Sinh'),
(7, 'Sử'),
(8, 'Địa'),
(9, 'GDCD'),
(10, 'Tin học'),
(11, 'Công nghệ'),
(12, 'Thể dục'),
(13, 'Quốc phòng');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users_admin`
--

CREATE TABLE `users_admin` (
  `id` bigint NOT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `image_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `users_admin`
--

INSERT INTO `users_admin` (`id`, `username`, `password`, `role`, `email`, `phone`, `image_url`) VALUES
(3, 'viu106018', '$2a$10$GLGXwjVpn.dznkwxOsz6iOM9dVMg/JfHa0R8ay48VYibIPA6SmD8u', 'ROLE_ADMIN', 'viu106018@donga.edu.vn', '0367604684', 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pinterest.com%2Fstarmanh%2Favatar-facebook%2F&psig=AOvVaw2sm0f-iaWQQze72W4YuLAu&ust=1748957676635000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCMDus5vt0o0DFQAAAAAdAAAAABAE'),
(5, 'viu852005', '$2a$12$axP4gVxYOqfERUSuXHQYu.9RmoZ3VsGDMtoVC/d9ns4AmS2.Ql7p6', 'ROLE_ADMIN', 'viuadmin@donga.edu.vn', '0367604685', 'https://i.pinimg.com/200x/c5/b9/ff/c5b9ffb0d6e4fad7e6f7df27f9545a93.jpg'),
(6, 'temp_admin', '$2a$10$1c0sC4c2n3KeOM4CU22L2u9Pd6RL8gDe0KLPO9xlz7oszrVndgqwq', 'ROLE_ADMIN', 'temp.admin@example.com', NULL, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users_students`
--

CREATE TABLE `users_students` (
  `id` bigint NOT NULL,
  `classroom` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `course` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `enrollment_date` date DEFAULT NULL,
  `full_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gender` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `major` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `student_code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `teacher_id` bigint DEFAULT NULL,
  `class_room_id` bigint DEFAULT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `avatar_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `birth_place` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ethnicity` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `father_job` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `father_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `father_phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `identity_issue_date` date DEFAULT NULL,
  `identity_issue_place` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `identity_number` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mother_job` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mother_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mother_phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nationality` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `religion` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `academic_performance` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `conduct` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `users_students`
--

INSERT INTO `users_students` (`id`, `classroom`, `course`, `email`, `enrollment_date`, `full_name`, `gender`, `major`, `phone`, `status`, `student_code`, `username`, `password`, `role`, `teacher_id`, `class_room_id`, `address`, `avatar_url`, `date_of_birth`, `birth_place`, `ethnicity`, `father_job`, `father_name`, `father_phone`, `identity_issue_date`, `identity_issue_place`, `identity_number`, `mother_job`, `mother_name`, `mother_phone`, `nationality`, `religion`, `academic_performance`, `conduct`) VALUES
(4, '', '2023-2025', 'hoaianh@tranning.edu.vn', '2025-07-02', 'Lệ Thị Hoài Anh', 'Nữ', NULL, '0367604684', 'Bảo lưu', 'K240003', 'hoaianh003', '$2a$10$AMA/Gj8Q.cdkI//J3.E/8ObswTVXstDj4ZgkJLb50kf.MEUUql9GC', 'STUDENT', NULL, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(5, '', '2023-2025', 'vanquy001@tranning.edu.vn', '2025-07-02', 'Nguyễn văn Quy', 'Nam', 'Văn', '0367604684', 'Đang học', 'K0001', 'vanquy001', '$2a$10$lrX/NQy12rzZROaPkQYs7u1jasRu.y/VVKvsJbd5EdEdNeK79dyhi', 'STUDENT', NULL, 2, '', 'https://cellphones.com.vn/sforum/wp-content/uploads/2023/11/avatar-vo-tri-54.jpg', NULL, 'Gia lai', '', '', '', '', NULL, '', '', '', '', '', '', '', NULL, NULL),
(6, '', '2023-2025', 'rmahviu005@tranning.edu.vn', '2022-02-02', 'rmahviu', 'Nam', 'Văn', '0367604684', 'Đang học', 'K0005', 'rmahviu005', '$2a$10$3rvaqhoRFDUHWUxF0CMro.r7qlzhm672t36rs2ZpE5BfTPwngAaXS', 'STUDENT', NULL, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(7, '', NULL, 'thihau006@tranning.edu.vn', '2025-06-04', 'Lê Thị Hậu', 'Nữ', NULL, '0367604684', 'Đang học', 'K0006', 'thihau006', '$2a$10$ANNBR/p/7zbCN806EBuR7O78kQxCsBTROaz6LRaf1AQoToMlQ7HXG', 'STUDENT', NULL, 3, 'Số 84, Phan Trọng Tuệ', 'https://cellphones.com.vn/sforum/wp-content/uploads/2024/02/avatar-anh-meo-cute-1.jpg', '2025-06-05', 'Gia lai', 'Jrai', '', 'Nguyễn văn A', '0123456789', '2025-06-05', '', '00000000076', '', 'Nguyễn thị B', '0123456789', 'Việt nam', 'không', NULL, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users_teachers`
--

CREATE TABLE `users_teachers` (
  `id` bigint NOT NULL,
  `avatar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` date DEFAULT NULL,
  `degree` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `department` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `full_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `position` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `degree_id` bigint DEFAULT NULL,
  `department_id` bigint DEFAULT NULL,
  `position_id` bigint DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `users_teachers`
--

INSERT INTO `users_teachers` (`id`, `avatar`, `created_at`, `degree`, `department`, `email`, `full_name`, `position`, `status`, `username`, `degree_id`, `department_id`, `position_id`, `password`, `role`, `phone`) VALUES
(3, 'https://moc247.com/wp-content/uploads/2023/12/loa-mat-voi-101-hinh-anh-avatar-meo-cute-dang-yeu-dep-mat_1-1.jpg', '2025-06-01', 'Thạc sĩ', 'Tổ Văn', 'rmahviu@tranning.edu.vn', 'Rmah Viu', 'Giáo viên', 'Đang công tác', 'rmahviu123', NULL, NULL, NULL, '$2a$10$KwUsWOnG1jCnbwvEJZx87OGtkuYnExFRXKkvaegyy.Ggo3n.VXwye', 'TEACHER', NULL),
(4, 'https://moc247.com/wp-content/uploads/2023/12/loa-mat-voi-101-hinh-anh-avatar-meo-cute-dang-yeu-dep-mat_2.jpg', '2025-06-01', 'Tiến sĩ', 'Tổ Lý', 'hoaianhgv@tranning.edu.vn', 'Nguễn Thi Hoài Trang', 'Hiệu trưởng', 'Đang công tác', 'hoaianhgv', NULL, NULL, NULL, '$2a$10$sVl0dJbo5uYPGCsopYo/8e7y0cfU.o686lvnA2pBc4p9z6a/99NoO', 'TEACHER', NULL);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `assignment`
--
ALTER TABLE `assignment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK4et3g8ilsouug6cv8590fqny6` (`class_room_id`),
  ADD KEY `FK7xj783jsyns1dr5ab75xa97jh` (`teacher_id`);

--
-- Chỉ mục cho bảng `attendance`
--
ALTER TABLE `attendance`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK9g5sauxymrdjel9md3mcy8f4b` (`class_room_id`),
  ADD KEY `FK7byf2yqtucteop90n4x7mcbke` (`student_id`);

--
-- Chỉ mục cho bảng `class_assignment`
--
ALTER TABLE `class_assignment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK33r1ca2iw4xne4o1adt96rp0g` (`class_room_id`),
  ADD KEY `FKmxus66dw9gmicd2q1cixikvul` (`teacher_id`);

--
-- Chỉ mục cho bảng `class_room`
--
ALTER TABLE `class_room`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKs2vb2icokx5su5bylfwe6b4gq` (`homeroom_teacher_id`);

--
-- Chỉ mục cho bảng `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKa0p6axib5gfy574i53j5qriis` (`class_room_id`),
  ADD KEY `FK82ln3kv58bv8o4kuukl1yvytc` (`teacher_id`);

--
-- Chỉ mục cho bảng `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKeob7loiu02sdyvrcio8yhwssu` (`teacher_id`),
  ADD KEY `FK9quk3ymql2ta5g4289kah8yyy` (`class_room_id`);

--
-- Chỉ mục cho bảng `degrees`
--
ALTER TABLE `degrees`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `departments`
--
ALTER TABLE `departments`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `exam_schedule`
--
ALTER TABLE `exam_schedule`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `grade`
--
ALTER TABLE `grade`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKpdum5objkbnr2q2xcpt5qk6i7` (`student_id`),
  ADD KEY `FKhhw6hbmiyabjlm1jghr00m5d8` (`subject_id`);

--
-- Chỉ mục cho bảng `period`
--
ALTER TABLE `period`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `positions`
--
ALTER TABLE `positions`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `schedule`
--
ALTER TABLE `schedule`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKa1dn4nhjtoq9o6b08ng7gu497` (`class_id`),
  ADD KEY `FKfce80ugg9yvmelgx40fho0y3j` (`period_id`),
  ADD KEY `FKh2hdhbss2x31ns719hka6enma` (`room_id`),
  ADD KEY `FK69oakeanwehikps300emu9sy4` (`subject_id`),
  ADD KEY `FKg4wcdlvg1h335v9c07behi4j7` (`teacher_id`);

--
-- Chỉ mục cho bảng `subject`
--
ALTER TABLE `subject`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `users_admin`
--
ALTER TABLE `users_admin`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `phone` (`phone`);

--
-- Chỉ mục cho bảng `users_students`
--
ALTER TABLE `users_students`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKg6vxpxqnnydyr9fhf8vmc0n85` (`teacher_id`),
  ADD KEY `FKk79ld99lqsgp3w18hs9jfwnv8` (`class_room_id`);

--
-- Chỉ mục cho bảng `users_teachers`
--
ALTER TABLE `users_teachers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `phone` (`phone`),
  ADD KEY `FKspyctdal77gnu1gi5kenno5uk` (`degree_id`),
  ADD KEY `FKb1gg2ipftq2n7wyb5ssmx8nnf` (`department_id`),
  ADD KEY `FKd1ofkyiwvj4r2bflca3fs6inp` (`position_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `assignment`
--
ALTER TABLE `assignment`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `attendance`
--
ALTER TABLE `attendance`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `class_assignment`
--
ALTER TABLE `class_assignment`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `class_room`
--
ALTER TABLE `class_room`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `course`
--
ALTER TABLE `course`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `courses`
--
ALTER TABLE `courses`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `degrees`
--
ALTER TABLE `degrees`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `departments`
--
ALTER TABLE `departments`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `exam_schedule`
--
ALTER TABLE `exam_schedule`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `grade`
--
ALTER TABLE `grade`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `period`
--
ALTER TABLE `period`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `positions`
--
ALTER TABLE `positions`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `room`
--
ALTER TABLE `room`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=506;

--
-- AUTO_INCREMENT cho bảng `schedule`
--
ALTER TABLE `schedule`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `subject`
--
ALTER TABLE `subject`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT cho bảng `users_admin`
--
ALTER TABLE `users_admin`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `users_students`
--
ALTER TABLE `users_students`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `users_teachers`
--
ALTER TABLE `users_teachers`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `assignment`
--
ALTER TABLE `assignment`
  ADD CONSTRAINT `FK4et3g8ilsouug6cv8590fqny6` FOREIGN KEY (`class_room_id`) REFERENCES `class_room` (`id`),
  ADD CONSTRAINT `FK7xj783jsyns1dr5ab75xa97jh` FOREIGN KEY (`teacher_id`) REFERENCES `users_teachers` (`id`);

--
-- Các ràng buộc cho bảng `attendance`
--
ALTER TABLE `attendance`
  ADD CONSTRAINT `FK7byf2yqtucteop90n4x7mcbke` FOREIGN KEY (`student_id`) REFERENCES `users_students` (`id`),
  ADD CONSTRAINT `FK9g5sauxymrdjel9md3mcy8f4b` FOREIGN KEY (`class_room_id`) REFERENCES `class_room` (`id`);

--
-- Các ràng buộc cho bảng `class_assignment`
--
ALTER TABLE `class_assignment`
  ADD CONSTRAINT `FK33r1ca2iw4xne4o1adt96rp0g` FOREIGN KEY (`class_room_id`) REFERENCES `class_room` (`id`),
  ADD CONSTRAINT `FKmxus66dw9gmicd2q1cixikvul` FOREIGN KEY (`teacher_id`) REFERENCES `users_teachers` (`id`);

--
-- Các ràng buộc cho bảng `class_room`
--
ALTER TABLE `class_room`
  ADD CONSTRAINT `FKs2vb2icokx5su5bylfwe6b4gq` FOREIGN KEY (`homeroom_teacher_id`) REFERENCES `users_teachers` (`id`);

--
-- Các ràng buộc cho bảng `course`
--
ALTER TABLE `course`
  ADD CONSTRAINT `FK82ln3kv58bv8o4kuukl1yvytc` FOREIGN KEY (`teacher_id`) REFERENCES `users_teachers` (`id`),
  ADD CONSTRAINT `FKa0p6axib5gfy574i53j5qriis` FOREIGN KEY (`class_room_id`) REFERENCES `class_room` (`id`);

--
-- Các ràng buộc cho bảng `courses`
--
ALTER TABLE `courses`
  ADD CONSTRAINT `FK9quk3ymql2ta5g4289kah8yyy` FOREIGN KEY (`class_room_id`) REFERENCES `class_room` (`id`),
  ADD CONSTRAINT `FKeob7loiu02sdyvrcio8yhwssu` FOREIGN KEY (`teacher_id`) REFERENCES `users_teachers` (`id`);

--
-- Các ràng buộc cho bảng `grade`
--
ALTER TABLE `grade`
  ADD CONSTRAINT `FKhhw6hbmiyabjlm1jghr00m5d8` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`),
  ADD CONSTRAINT `FKpdum5objkbnr2q2xcpt5qk6i7` FOREIGN KEY (`student_id`) REFERENCES `users_students` (`id`);

--
-- Các ràng buộc cho bảng `schedule`
--
ALTER TABLE `schedule`
  ADD CONSTRAINT `FK69oakeanwehikps300emu9sy4` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`),
  ADD CONSTRAINT `FKa1dn4nhjtoq9o6b08ng7gu497` FOREIGN KEY (`class_id`) REFERENCES `class_room` (`id`),
  ADD CONSTRAINT `FKfce80ugg9yvmelgx40fho0y3j` FOREIGN KEY (`period_id`) REFERENCES `period` (`id`),
  ADD CONSTRAINT `FKg4wcdlvg1h335v9c07behi4j7` FOREIGN KEY (`teacher_id`) REFERENCES `users_teachers` (`id`),
  ADD CONSTRAINT `FKh2hdhbss2x31ns719hka6enma` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`);

--
-- Các ràng buộc cho bảng `users_students`
--
ALTER TABLE `users_students`
  ADD CONSTRAINT `FKg6vxpxqnnydyr9fhf8vmc0n85` FOREIGN KEY (`teacher_id`) REFERENCES `users_teachers` (`id`),
  ADD CONSTRAINT `FKk79ld99lqsgp3w18hs9jfwnv8` FOREIGN KEY (`class_room_id`) REFERENCES `class_room` (`id`);

--
-- Các ràng buộc cho bảng `users_teachers`
--
ALTER TABLE `users_teachers`
  ADD CONSTRAINT `FKb1gg2ipftq2n7wyb5ssmx8nnf` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`),
  ADD CONSTRAINT `FKd1ofkyiwvj4r2bflca3fs6inp` FOREIGN KEY (`position_id`) REFERENCES `positions` (`id`),
  ADD CONSTRAINT `FKspyctdal77gnu1gi5kenno5uk` FOREIGN KEY (`degree_id`) REFERENCES `degrees` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
