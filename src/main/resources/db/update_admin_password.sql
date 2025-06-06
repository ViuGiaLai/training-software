-- Đổi mật khẩu mới (ví dụ: 123456) cho user admin có id = 3 (BCrypt)
UPDATE users_admin
SET password = '$2a$10$GLGXwjVpn.dznkwxOsz6iO9MdVMg/JfHa0R8ay48VYi bIPAGSmD8u'
WHERE id = 3;
