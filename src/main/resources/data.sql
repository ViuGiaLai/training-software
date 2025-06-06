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
-- Đang đổ dữ liệu cho bảng `class_room`
--

INSERT INTO `class_room` (`id`, `name`, `student_count`, `grade`, `subject`, `academic_year`, `code`, `description`, `homeroom_teacher_id`) VALUES
(2, '12A1', NULL, '12', NULL, '2023-2025', '760616', '', 4),
(3, '12A2', NULL, '12', NULL, '2023-2028', '00920', '', 3);

-- --------------------------------------------------------

--
-- Đang đổ dữ liệu cho bảng `exam_schedule`
--

INSERT INTO `exam_schedule` (`id`, `classes`, `end_time`, `number_of_students`, `rooms`, `start_time`, `status`, `subject`) VALUES
(1, '12A1', '2025-06-07 14:14:00.000000', 3, 'Phòng 202', '2025-06-07 09:14:00.000000', 'Chờ thi', 'Văn');

-- --------------------------------------------------------

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
-- Đang đổ dữ liệu cho bảng `schedule`
--

INSERT INTO `schedule` (`id`, `date`, `class_id`, `period_id`, `room_id`, `subject_id`, `teacher_id`) VALUES
(1, '2025-06-04', 2, 3, 203, 3, 3),
(2, '2025-06-06', 2, 5, 303, 6, 4),
(3, '2025-06-06', 3, 1, 204, 10, 4);

-- --------------------------------------------------------

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
-- Đang đổ dữ liệu cho bảng `users_admin`
--

INSERT INTO `users_admin` (`id`, `username`, `password`, `role`, `email`, `phone`, `image_url`) VALUES
(3, 'viu106018', '$2a$10$GLGXwjVpn.dznkwxOsz6iOM9dVMg/JfHa0R8ay48VYibIPA6SmD8u', 'ROLE_ADMIN', 'viu106018@donga.edu.vn', '0367604684', 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pinterest.com%2Fstarmanh%2Favatar-facebook%2F&psig=AOvVaw2sm0f-iaWQQze72W4YuLAu&ust=1748957676635000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCMDus5vt0o0DFQAAAAAdAAAAABAE'),
(5, 'viu852005', '$2a$12$axP4gVxYOqfERUSuXHQYu.9RmoZ3VsGDMtoVC/d9ns4AmS2.Ql7p6', 'ROLE_ADMIN', 'viuadmin@donga.edu.vn', '0367604685', 'https://i.pinimg.com/200x/c5/b9/ff/c5b9ffb0d6e4fad7e6f7df27f9545a93.jpg'),
(6, 'temp_admin', '$2a$10$1c0sC4c2n3KeOM4CU22L2u9Pd6RL8gDe0KLPO9xlz7oszrVndgqwq', 'ROLE_ADMIN', 'temp.admin@example.com', NULL, NULL);

-- --------------------------------------------------------

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
-- Đang đổ dữ liệu cho bảng `users_teachers`
--

INSERT INTO `users_teachers` (`id`, `avatar`, `created_at`, `degree`, `department`, `email`, `full_name`, `position`, `status`, `username`, `degree_id`, `department_id`, `position_id`, `password`, `role`, `phone`) VALUES
(3, 'https://moc247.com/wp-content/uploads/2023/12/loa-mat-voi-101-hinh-anh-avatar-meo-cute-dang-yeu-dep-mat_1-1.jpg', '2025-06-01', 'Thạc sĩ', 'Tổ Văn', 'rmahviu@tranning.edu.vn', 'Rmah Viu', 'Giáo viên', 'Đang công tác', 'rmahviu123', NULL, NULL, NULL, '$2a$10$KwUsWOnG1jCnbwvEJZx87OGtkuYnExFRXKkvaegyy.Ggo3n.VXwye', 'TEACHER', NULL),
(4, 'https://moc247.com/wp-content/uploads/2023/12/loa-mat-voi-101-hinh-anh-avatar-meo-cute-dang-yeu-dep-mat_2.jpg', '2025-06-01', 'Tiến sĩ', 'Tổ Lý', 'hoaianhgv@tranning.edu.vn', 'Nguễn Thi Hoài Trang', 'Hiệu trưởng', 'Đang công tác', 'hoaianhgv', NULL, NULL, NULL, '$2a$10$sVl0dJbo5uYPGCsopYo/8e7y0cfU.o686lvnA2pBc4p9z6a/99NoO', 'TEACHER', NULL);

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
