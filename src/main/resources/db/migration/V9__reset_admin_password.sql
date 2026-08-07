-- Reset admin password to admin123 using standard BCrypt hash
update departments set password = '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi' where email = 'admin@s2dcms.com';
